namespace lab13_MAP.source.domain;

public class Entity<T>
{
    public T Id { get; set; }

    public override string ToString()
    {
        return Id.ToString();
    }
}