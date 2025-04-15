using moto.model;

namespace moto.persistence;

public interface IUserRepository:IRepository<int,User>
{
    public User? FindByUsername(string username, string password);
}