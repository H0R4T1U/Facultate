namespace lab13_MAP.source.domain;

public class Student : Entity<int>
{
    public string Name { get; }
    public string School { get; }

    protected Student(int id, string name, string school)
    {
        Id = id;
        Name = name;
        School = school;
    }

    public override string ToString()
    {
        return base.ToString() + ',' + Name + ',' + School;
        
    }

    public string To()
    {
        return "Name: " + Name + "School:" + School;
    }
}