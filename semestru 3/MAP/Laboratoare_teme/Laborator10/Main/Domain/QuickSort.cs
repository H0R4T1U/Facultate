
namespace Main.Domain;

public class QuickSort : AbstractSorter
{
    private static int Partition(int[] arr, int low, int high) {
        var pivot = arr[high];
        var i = low - 1;
        for (var j = low; j <= high - 1; j++)
        {
            if (arr[j] >= pivot) continue;
            i++;
            Swap(arr, i, j);
        }
        Swap(arr, i + 1, high);
        return i + 1;
    }

    private static void Swap(int[] arr, int i, int j) {
        (arr[i], arr[j]) = (arr[j], arr[i]);
    }

    private static void quickSort(int[] arr, int low, int high)
    {
        while (true)
        {
            if (low >= high) return;
            var pi = Partition(arr, low, high);
            quickSort(arr, low, pi - 1);
            low = pi + 1;
        }
    }

    public override void Sort(int[] array)
    {
        quickSort(array, 0, array.Length - 1);
        Console.WriteLine("Quick Sort");
    }
}