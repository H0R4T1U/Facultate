namespace Seminar_11;

interface IRepository<ID, E> where E : Entity<ID>
{
    E FindOne(ID id);
    public IEnumerable<E> FindAll();
    E Save(E entity);
    E Delete(ID id);
    E Update(E entity);
}