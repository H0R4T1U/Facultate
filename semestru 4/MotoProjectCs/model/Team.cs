namespace moto.model;

public class Team : Entity<int>
{
    public string Name { get; set; }

    public Team(string name)
    {
        Name = name;
    }
}