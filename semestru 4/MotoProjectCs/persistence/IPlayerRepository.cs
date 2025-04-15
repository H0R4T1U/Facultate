using moto.model;

namespace moto.persistence;

public interface IPlayerRepository: IRepository<int,Player>
{
    IDictionary<int, Player> FindPlayersByTeam(int teamId);
    
}