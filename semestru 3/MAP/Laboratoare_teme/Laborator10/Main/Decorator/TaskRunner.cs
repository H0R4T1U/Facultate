
using Task = Main.Domain.Task;

namespace Main.Decorator;

public interface ITaskRunner
{
    void ExecuteOneTask();
    void ExecuteAll();
    void AddTask(Task t);
    bool HasTask();
}