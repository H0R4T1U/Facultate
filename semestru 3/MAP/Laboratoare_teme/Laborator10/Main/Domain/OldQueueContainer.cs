namespace Main.Domain;

public class OldQueueContainer : IContainer
{
    private readonly List<Task> _tasks = [];
    
    public Task Remove()
    {
        if (_tasks.Count != 0) return null!;
        var task = _tasks.ElementAt(0);
        _tasks.RemoveAt(0);
        return task;
    }

    public void Add(Task task)
    {
        _tasks.Add(task);
    }

    public int Size()
    {
        return _tasks.Count;
    }

    public bool IsEmpty()
    {
        return _tasks.Count == 0;
    }
}