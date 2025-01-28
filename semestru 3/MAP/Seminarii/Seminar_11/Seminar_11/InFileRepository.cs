namespace Seminar_11;

public class InFileRepository<ID,E> : InMemoryRepository<ID,E> where E : Entity<ID>
{
    private String FileName;
    public InFileRepository(IValidator<E> vali,String fileName) : base(vali)
    {
        fileName = fileName;
    }

    public void Load()
    {
        IEnumerable<String> text = System.IO.File.ReadLines(FileName);
        foreach (var line in text)
        {

        }   
    }
}