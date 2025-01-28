
namespace Main.Domain;

public class BubbleSort : AbstractSorter
{
    public override void Sort(int[] array)
    {
        for (var i = 0; i < array.Length - 1; i++)
        {
            var swapped = false;
            for (var j = 0; j < array.Length - i - 1; j++)
                if (array[j] > array[j + 1])
                {
                    (array[j], array[j + 1]) = (array[j + 1], array[j]);
                    swapped = true;
                }
            if (!swapped)
                return;
        }
        Console.WriteLine("Bubble sort");
    }
}