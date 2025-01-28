namespace Main.Domain;

public abstract class AbstractContainer : IContainer
{
    protected readonly List<Task> Tasks = [];

    public abstract Task Remove();

    public abstract void Add(Task task);

    public int Size()
    {
        return Tasks.Count;
    }

    public bool IsEmpty()
    {
        return Tasks.Count == 0;
    }
}