namespace lab13_MAP.source.dto;

public class MatchDto(string firstTeam, string secondTeam, DateTime matchDate)
{
    public override string ToString()
    {
        return "Match:\nFirst team: " + firstTeam + "\nSecond team:" + secondTeam + "\nMatch Date:" + matchDate;
    }
}