using Main.Factory;

namespace Main.Domain;

public class SortingTask : Task
{
    private readonly AbstractSorter _sorter;
    private readonly int[] _vector;

    public SortingTask(long id, string description, SortingStrategy strategy, int[] vector)
        : base(id, description)
    {
        _vector = vector;
        _sorter = strategy == SortingStrategy.Bubble 
            ? new BubbleSort() 
            : new QuickSort();
    }

    public override void Execute()
    {
        _sorter.Sort(_vector);
        foreach (var i in _vector)
        {
            Console.Write(i + " ");
        }
    }
}