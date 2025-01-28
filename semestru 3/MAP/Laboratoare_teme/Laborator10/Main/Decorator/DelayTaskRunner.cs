
namespace Main.Decorator;

public class DelayTaskRunner(ITaskRunner taskRunner) : AbstractTaskRunner(taskRunner)
{
    public override void ExecuteOneTask()
    {
        try
        {
            Thread.Sleep(3000);
            Console.WriteLine("Delay Task");
            base.ExecuteOneTask();
        }
        catch (Exception ex)
        {
            Console.WriteLine(ex.Message);
        }
    }

    public override void ExecuteAll()
    {
        while (HasTask())
        {
            ExecuteOneTask();
        }
    }
}