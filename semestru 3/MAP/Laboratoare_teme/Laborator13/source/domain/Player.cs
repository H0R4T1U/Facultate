namespace lab13_MAP.source.domain;

public class Player : Student
{
    public int TeamId { get; }
    
    public Player(int id, string name, string school, int teamId) : base(id, name, school)
    {
        Id = id;
        TeamId = teamId;
    }

    public override string ToString()
    {
        return base.ToString() + ',' + TeamId;
    }
}