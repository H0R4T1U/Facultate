namespace Lab_11;

public class Person
{
    protected int Age { get; set; }

    public void Greet()
    {
        Console.WriteLine("Hello");
    }

    public void SetAge(int age)
    {
        this.Age = age;
    }
    
}