using lab13_MAP.source.domain;

namespace lab13_MAP.source.repository;

public interface IRepository<in TId, TE> where TE : Entity<TId>
{
    TE FindOne(TId id);
    IEnumerable<TE> FindAll();
    TE Save(TE entity);
}