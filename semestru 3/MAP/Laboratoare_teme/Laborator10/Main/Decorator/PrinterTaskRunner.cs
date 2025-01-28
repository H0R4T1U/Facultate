using System.Globalization;

namespace Main.Decorator;

public class PrinterTaskRunner(ITaskRunner taskRunner) : AbstractTaskRunner(taskRunner)
{
    private static readonly DateTimeFormatInfo DateTimeFormat = DateTimeFormatInfo.InvariantInfo;

    public override void ExecuteOneTask()
    {
        base.ExecuteOneTask();
        Console.WriteLine("Task executed at: " + DateTime.Now.ToString(DateTimeFormat));
    }

    public override void ExecuteAll()
    {
        while (HasTask())
        {
            ExecuteOneTask();
        }
    }
}