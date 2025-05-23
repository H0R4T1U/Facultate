using moto.model;

namespace moto.persistence;

public interface IRepository<TId, TE> where TE : Entity<TId>
{
    
    TE? FindOne(TId id);
    IDictionary<TId,TE> FindAll();
    TE? Save(TE entity);
    TE? Delete(TId id);
    TE? Update(TE entity);
}