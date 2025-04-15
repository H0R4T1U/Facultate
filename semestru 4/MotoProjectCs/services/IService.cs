using moto.model;

namespace moto.services;

public interface IService {
    public User FindByUsername(String username);
    public void Login(User user,IObserver client);
    public void Logout(User user,IObserver client);
    public void Add(Player p,int raceId,IObserver client);
    public Team? GetTeamByName(String name);
    public Race[] GetAllRaces();
    public Player[] GetRacePlayersOfTeam(int raceId, int teamId);

}
