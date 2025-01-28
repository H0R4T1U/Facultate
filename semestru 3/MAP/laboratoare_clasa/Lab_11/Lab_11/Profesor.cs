namespace Lab_11;

public class Profesor : Person
{
    private string Subject { get; set; }

    public void Explain()
    {
        Console.WriteLine("Explanation begins:");
    }
}