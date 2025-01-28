using lab13_MAP.source.domain;

namespace lab13_MAP.source.repository;

public abstract class AbstractRepository<TId, TE>: IRepository<TId, TE> where TE : Entity<TId>
{
    protected AbstractRepository()
    {
        _entities = new Dictionary<TId, TE>();
        Load();
    }
    
    private readonly Dictionary<TId, TE> _entities;
    protected abstract TE CreateEntity(string line);

    protected abstract string GetFilePath();
    
    public TE FindOne(TId id)
    {
        return _entities.GetValueOrDefault(id);
    }

    public IEnumerable<TE> FindAll()
    {
        return _entities.Values;
    }

    public TE Save(TE entity)
    {
        _entities.Add(entity.Id, entity);
        Store();
        return entity;
    }

    private void Load()
    {
        using var streamReader = new StreamReader(new FileStream(GetFilePath(), FileMode.Open));
        while (streamReader.ReadLine() is { } line)
        {
            var entity = CreateEntity(line);
            _entities.Add(entity.Id, entity);
        }
    }

    private void Store()
    {
        using var streamWriter = new StreamWriter(new FileStream(GetFilePath(), FileMode.Create));
        foreach (var keyValuePair in _entities)
        {
            streamWriter.WriteLine(keyValuePair.Value.ToString());
        }
    }

}