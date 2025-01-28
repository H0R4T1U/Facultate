using System.Collections;

namespace Main.Domain;

public abstract class ContainerSuperclass : IContainer
{
    private readonly ArrayList _tasks = new();

    public abstract Task Remove();

    public abstract void Add(Task task);

    public int Size()
    {
        return _tasks.Count;
    }

    public bool IsEmpty()
    {
        return _tasks.Count == 0;
    }
}