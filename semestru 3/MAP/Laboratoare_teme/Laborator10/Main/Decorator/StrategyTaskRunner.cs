using Main.Domain;
using Main.Factory;
using Task = Main.Domain.Task;

namespace Main.Decorator;

public class StrategyTaskRunner(Strategy strategy) : ITaskRunner
{
    private readonly IContainer _container = new TaskContainerFactory().CreateContainer(strategy);

    public void ExecuteOneTask()
    {
        var task = _container.Remove();
        task.Execute();
    }

    public void ExecuteAll()
    {
        while (HasTask())
            ExecuteOneTask();
    }

    public void AddTask(Task t)
    {
        _container.Add(t);
    }

    public bool HasTask()
    {
        return !_container.IsEmpty();
    }
}