using System.Globalization;


namespace Main.Domain;

public class MessageTask(long id, string description, string date, string to, string from, DateTime message) : Task(id, description)
{
    private static readonly DateTimeFormatInfo DateFormatter = DateTimeFormatInfo.InvariantInfo;
    
    public override void Execute()
    {
        Console.WriteLine(date.ToString(DateFormatter) + ": " + message);
    }

    public override string ToString()
    {
        return "id = " + Id +
               " | description = " + Description +
               " | message = " + message +
               " | from = " + from +
               " | to = " + to +
               " | date = " + date.ToString(DateFormatter);
    }
}