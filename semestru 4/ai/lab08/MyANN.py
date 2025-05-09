import numpy as np

class MyMLPClassifier:
    def __init__(self, hidden_layer_sizes=(100,), activation='relu', alpha=0.0001, 
                 learning_rate_init=0.001, max_iter=200, random_state=None, verbose=False):
        self.hidden_layer_sizes = hidden_layer_sizes
        self.activation = activation
        self.alpha = alpha
        self.learning_rate_init = learning_rate_init
        self.max_iter = max_iter
        self.random_state = random_state
        self.verbose = verbose
        self.weights = []
        self.biases = []
        
        if random_state is not None:
            np.random.seed(random_state)
    
    def _initialize_weights_and_biases(self, input_size, output_size):
        layer_sizes = [input_size] + list(self.hidden_layer_sizes) + [output_size]
        
        for i in range(1, len(layer_sizes)):
            scale = np.sqrt(2.0 / (layer_sizes[i - 1] + layer_sizes[i]))
            self.weights.append(np.random.randn(layer_sizes[i - 1], layer_sizes[i]) * scale)
            self.biases.append(np.zeros((1, layer_sizes[i])))

    def _activation_function(self, z):
        if self.activation == 'relu':
            return np.maximum(0, z)
        elif self.activation == 'tanh':
            return np.tanh(z)
        elif self.activation == 'sigmoid':
            return 1 / (1 + np.exp(-np.clip(z, -500, 500)))  
        else:
            raise ValueError("Invalid activation function. Supported activations: 'relu', 'tanh', 'sigmoid'.")
    
    def _derivative_activation_function(self, z):
        if self.activation == 'relu':
            return np.where(z > 0, 1, 0)
        elif self.activation == 'tanh':
            return 1 - np.power(np.tanh(z), 2)
        elif self.activation == 'sigmoid':
            s = 1 / (1 + np.exp(-np.clip(z, -500, 500)))
            return s * (1 - s)
        else:
            raise ValueError("Invalid activation function. Supported activations: 'relu', 'tanh', 'sigmoid'.")
    
    def _forward_pass(self, X):
        activations = [X]
        z_values = []
        
        for i in range(len(self.weights)):
            z = np.dot(activations[-1], self.weights[i]) + self.biases[i]
            z_values.append(z)
            
            a = self._activation_function(z)
            activations.append(a)
            
        return activations, z_values
    
    def _compute_loss(self, y_true, y_pred):
        epsilon = 1e-15  
        y_pred = np.clip(y_pred, epsilon, 1 - epsilon)
        loss = -np.mean(y_true * np.log(y_pred) + (1 - y_true) * np.log(1 - y_pred))
        return loss
        
    def fit(self, X, y):
        if y.ndim == 1:
            y = y.reshape(-1, 1)
        
        n_classes = max(1, np.max(y) + 1)
        
        self._initialize_weights_and_biases(X.shape[1], n_classes if n_classes > 2 else 1)
        
        if n_classes > 2:
            y_onehot = np.zeros((y.shape[0], n_classes))
            for i in range(y.shape[0]):
                y_onehot[i, y[i]] = 1
            y = y_onehot
            
        for iteration in range(self.max_iter):
            activations, z_values = self._forward_pass(X)
            
            if self.verbose and iteration % 100 == 0:
                loss = self._compute_loss(y, activations[-1])
                print(f"Iteration {iteration}, Loss: {loss:.6f}")
            

            delta = activations[-1] - y
            
            deltas = [delta]
            for layer_idx in range(len(self.weights) - 1, 0, -1):
                delta = np.dot(delta, self.weights[layer_idx].T) * self._derivative_activation_function(z_values[layer_idx - 1])
                deltas.insert(0, delta)
            
            for layer_idx in range(len(self.weights)):
                dW = np.dot(activations[layer_idx].T, deltas[layer_idx]) / X.shape[0]
                db = np.mean(deltas[layer_idx], axis=0, keepdims=True)
                
                dW += (self.alpha * self.weights[layer_idx])
                
                self.weights[layer_idx] -= self.learning_rate_init * dW
                self.biases[layer_idx] -= self.learning_rate_init * db
        
        return self
        
    def predict_proba(self, X):
        activations, _ = self._forward_pass(X)
        return activations[-1]
    
    def predict(self, X):
        y_prob = self.predict_proba(X)
        
        if y_prob.shape[1] == 1:  
            return (y_prob >= 0.5).astype(int)
        else:  
            return np.argmax(y_prob, axis=1).reshape(-1, 1)