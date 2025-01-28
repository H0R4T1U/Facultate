namespace lab13_MAP.source.domain;

public class Match : Entity<int>
{
    public Match(int id, int firstTeamId, int secondTeamId, DateTime matchDate)
    {
        Id = id;
        FirstTeamId = firstTeamId;
        SecondTeamId = secondTeamId;
        MatchDate = matchDate;
    }

    public int FirstTeamId { get; }

    public int SecondTeamId { get; }

    public DateTime MatchDate { get; }

    public override string ToString()
    {
        return base.ToString() + ',' + FirstTeamId + ',' + SecondTeamId + ',' + MatchDate;
    }
}