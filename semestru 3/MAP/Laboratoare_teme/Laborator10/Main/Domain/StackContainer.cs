
namespace Main.Domain;

public class StackContainer : AbstractContainer
{
    public override Task Remove()
    {
        if (IsEmpty()) return null!;
        var lastTask = Tasks[^1];
        Tasks.RemoveAt(Size() - 1);
        return lastTask;
    }

    public override void Add(Task task)
    {
        Tasks.Add(task);
    }
}