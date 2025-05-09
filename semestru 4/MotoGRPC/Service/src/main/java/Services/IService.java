//package Services;
//
//
//import model.Player;
//import model.Race;
//import model.Team;
//import model.User;
//
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//
//public interface IService {
//    public void login(User user,IObserver client) throws MotoException;
//    public void logout(User user,IObserver client) throws MotoException;
//    public void add(Player p,Integer raceId,IObserver client) throws MotoException;
//    public Optional<Team> getTeamByName(String name) throws MotoException;
//    public Race[] getAllRaces() throws MotoException;
//    public Player[] getRacePlayersOfTeam(Integer raceId, Integer teamId) throws MotoException;
//
//}
