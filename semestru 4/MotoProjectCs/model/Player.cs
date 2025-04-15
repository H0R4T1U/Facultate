namespace moto.model;


public class Player : Entity<int>
{
    public string Name { get; set; }
    public string Code { get; set; }
    public int Team { get; set; }

    public Player(string name, string code, int team)
    {
        Name = name;
        Code = code;
        Team = team;
    }
    
    public override string ToString()
    {
        return $"Player: {Name}, Code: {Code}, Team: {Team}";
    }

    public override bool Equals(object? obj)
    {
        return base.Equals(obj);
    }

    public override int GetHashCode()
    {
        return base.GetHashCode();
    }
}