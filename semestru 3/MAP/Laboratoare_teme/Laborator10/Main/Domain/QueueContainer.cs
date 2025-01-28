
using Main.Domain;
using Task = Main.Domain.Task;

public class QueueContainer : AbstractContainer
{
    public override Task Remove()
    {
        if (IsEmpty())
            return null!;
        var task = Tasks[0];
        Tasks.RemoveAt(0);
        return task;
    }

    public override void Add(Task task)
    {
        Tasks.Add(task);
    }
}