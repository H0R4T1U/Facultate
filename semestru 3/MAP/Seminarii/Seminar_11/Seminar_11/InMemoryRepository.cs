namespace Seminar_11;

public class InMemoryRepository<ID, E> : IRepository<ID, E> where E : Entity<ID>
{
    protected IValidator<E> vali;

    protected IDictionary<ID, E> entities = new Dictionary<ID, E>();

    public InMemoryRepository(IValidator<E> vali)
    {
        this.vali = vali;
    }

    public E FindOne(ID id)
    {
        return entities.ContainsKey(id) ? entities[id] : null;
    }

    public IEnumerable<E> FindAll()
    {
        return entities.Values;
    }

    public E Save(E entity)
    {
         entities[entity.ID] = entity;
         return entity;
    }

    public E Delete(ID id)
    {
        E entity = FindOne(id);
        entities.Remove(id);
        return entity;
    }

    public E Update(E entity)
    {
        entities[entity.ID] = entity;
        return entity;
    }
}