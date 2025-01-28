
namespace Main.Domain;

public interface IContainer
{
    Task Remove();
    void Add(Task task);
    int Size();
    bool IsEmpty();
}