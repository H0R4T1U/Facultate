
using Main.Domain;

namespace Main.Factory;

public class TaskContainerFactory : IFactory
{
    public IContainer CreateContainer(Strategy strategy)
    {
        if (strategy == Strategy.Fifo)
            return new QueueContainer();
        return new StackContainer();
    }
}