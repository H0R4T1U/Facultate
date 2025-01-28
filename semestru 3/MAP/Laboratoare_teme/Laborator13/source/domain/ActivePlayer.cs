using lab13_MAP.domain;

namespace lab13_MAP.source.domain;

public class ActivePlayer : Entity<int>
{
    public ActivePlayer(int id, string name, int matchId, int points, PlayerType type)
    {
        Id = id;
        Name = name;
        MatchId = matchId;
        Points = points;
        Type = type;
    }
    public string Name { get; }
    public int MatchId { get; }
    public int Points { get; }
    public PlayerType Type { get; }

    public override string ToString()
    {
        return base.ToString() + ',' + Name + ',' + MatchId + ',' + Points + ',' + Type;
    }
}