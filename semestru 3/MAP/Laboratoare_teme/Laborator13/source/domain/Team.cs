namespace lab13_MAP.source.domain;

public class Team : Entity<int>
{
    public Team(int id, string name)
    {
        Id = id;
        Name = name;
    }

    public string Name { get; }

    public override string ToString()
    {
        return base.ToString() + ',' + Name;
    }
}