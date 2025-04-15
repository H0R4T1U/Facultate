package Services;


import model.Player;
import model.Race;
import model.Team;
import model.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IService {
    public User findByUsername(String username);
    public int login(String username, String password);
    public void add(String name, String code, Integer team,Integer raceId);
    public Map<Integer, Player> getPlayersByTeam(Integer teamId);
    public Optional<Team> getTeamByName(String name);
    public Map<Integer, Race> getAllRaces();
    public List<Player> getRacePlayersOfTeam(Integer raceId, Integer teamId);

}
