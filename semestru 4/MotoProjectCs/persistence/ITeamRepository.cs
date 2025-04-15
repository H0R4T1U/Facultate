using moto.model;

namespace moto.persistence;

public interface ITeamRepository:IRepository<int,Team>
{
    public Team? GetTeamByName(string teamName);
}