import numpy as np

class ConvLayer:
    def __init__(self, num_kernels, kernel_size, input_shape):
        self.num_kernels = num_kernels
        self.kernel_size = kernel_size
        self.input_shape = input_shape
        self.kernels = np.random.randn(num_kernels, kernel_size, kernel_size, input_shape[-1]) / (kernel_size * kernel_size)
        self.biases = np.zeros((num_kernels, 1))

    def iterate_patches(self, input_image):
        height, width = input_image.shape[:2]
        for y in range(height - self.kernel_size + 1):
            for x in range(width - self.kernel_size + 1):
                patch = input_image[y:(y + self.kernel_size), x:(x + self.kernel_size)]
                yield patch, y, x

    def forward(self, image_input):
        self.input_cache = image_input
        height, width = image_input.shape[:2]
        output = np.zeros((height - self.kernel_size + 1, width - self.kernel_size + 1, self.num_kernels))

        for patch, y, x in self.iterate_patches(image_input):
            output[y, x] = np.sum(patch * self.kernels, axis=(1, 2, 3)) + self.biases.flatten()

        return output

    def backward(self, grad_output, lr):
        grad_kernels = np.zeros_like(self.kernels)
        grad_biases = np.sum(grad_output, axis=(0, 1, 2)).reshape(-1, 1)

        for patch, y, x in self.iterate_patches(self.input_cache):
            for k in range(self.num_kernels):
                grad_kernels[k] += grad_output[y, x, k] * patch

        self.kernels -= lr * grad_kernels
        self.biases -= lr * grad_biases

        return np.zeros_like(self.input_cache)


class MaxPoolLayer:
    def iterate_patches(self, input_volume):
        h, w, _ = input_volume.shape
        for y in range(h // 2):
            for x in range(w // 2):
                patch = input_volume[y * 2:y * 2 + 2, x * 2:x * 2 + 2]
                yield patch, y, x

    def forward(self, input_volume):
        self.input_cache = input_volume
        h, w, depth = input_volume.shape
        output = np.zeros((h // 2, w // 2, depth))

        for patch, y, x in self.iterate_patches(input_volume):
            output[y, x] = np.amax(patch, axis=(0, 1))
        return output


class DenseLayer:
    def __init__(self, input_len, output_len):
        self.weights = np.random.randn(input_len, output_len) * np.sqrt(2.0 / input_len)
        self.biases = np.zeros(output_len)

    def forward(self, flattened_input):
        self.input_shape = flattened_input.shape
        self.input_cache = flattened_input.flatten()
        return np.dot(self.input_cache, self.weights) + self.biases

    def backward(self, grad_output):
        self.grad_weights = np.dot(self.input_cache.reshape(-1, 1), grad_output.reshape(1, -1))
        self.grad_biases = grad_output
        return np.dot(grad_output, self.weights.T).reshape(self.input_shape)

    def update(self, lr):
        self.weights -= lr * self.grad_weights
        self.biases -= lr * self.grad_biases


class CNNModel:
    def __init__(self, input_dims, num_classes):
        self.input_dims = input_dims
        self.num_classes = num_classes

        self.conv1 = ConvLayer(num_kernels=8, kernel_size=3, input_shape=input_dims)
        self.pool1 = MaxPoolLayer()
        self.conv2 = ConvLayer(num_kernels=16, kernel_size=3, input_shape=(input_dims[0] // 2, input_dims[1] // 2, 8))
        self.pool2 = MaxPoolLayer()

        flattened_size = 3136
        self.fc1 = DenseLayer(flattened_size, 128)
        self.fc2 = DenseLayer(128, num_classes)

    def forward(self, input_data):
        x = self.conv1.forward(input_data)
        x = self.pool1.forward(x)
        x = self.conv2.forward(x)
        x = self.pool2.forward(x)
        x = x.reshape(-1, x.size)
        x = self.fc1.forward(x)
        x = self.fc2.forward(x)
        return x

    def backward(self, grad_output, lr):
        grad_fc1 = self.fc2.backward(grad_output)
        grad_conv2 = self.fc1.backward(grad_fc1)
        _ = self.conv2.backward(grad_conv2, lr)
        _ = self.conv1.backward(_, lr)
        self.fc1.update(lr)
        self.fc2.update(lr)

    def train(self, data_inputs, data_targets, lr, num_epochs):
        for epoch in range(num_epochs):
            for i in range(len(data_inputs)):
                x = data_inputs[i].reshape(self.input_dims)
                output = self.forward(x)
                loss = np.mean((output - data_targets[i]) ** 2)
                grad = 2 * (output - data_targets[i])
                self.backward(grad, lr)

    def predict(self, sample_input):
        return np.argmax(self.forward(sample_input), axis=1)
