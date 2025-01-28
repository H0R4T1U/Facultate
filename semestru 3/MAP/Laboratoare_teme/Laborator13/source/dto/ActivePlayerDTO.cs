namespace lab13_MAP.source.dto;

public class ActivePlayerDto(string name, string match, int points, string type)
{
    public override string ToString()
    {
        return "Name: " + name + "\n" + match + "\nPoints: " + points + "\nType: " + type + "\n";
    }
}