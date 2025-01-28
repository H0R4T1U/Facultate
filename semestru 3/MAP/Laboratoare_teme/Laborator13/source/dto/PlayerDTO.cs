namespace lab13_MAP.source.dto;

public class PlayerDto(string name, string school, string team)
{
    public override string ToString()
    {
        return "Name: " + name + "\nSchool: " + school + "\nTeam: " + team;
    }
}