package Services;


import model.Player;
import model.Race;
import model.Team;
import model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repository.PlayerRepository;
import repository.RaceRepository;
import repository.TeamRepository;
import repository.UserRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Service implements IService {
    private static final Logger log = LogManager.getLogger(Service.class);

    private final PlayerRepository playerRepository;
    private final UserRepository userRepository;
    private final RaceRepository raceRepository;
    private final TeamRepository teamRepository;

    public Service(PlayerRepository playerRepository, UserRepository userRepository, RaceRepository raceRepository, TeamRepository teamRepository) {
        this.playerRepository = playerRepository;
        this.userRepository = userRepository;
        this.raceRepository = raceRepository;
        this.teamRepository = teamRepository;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> {
            log.error("User not found");
            return new RuntimeException("User not found");
        });
    }

    public int login(String username, String password) {
        User user = findByUsername(username);
        if (user.getPassword().equals(password)) {
            return user.getId();
        }
        return -1;
    }

    public void add(String name, String code, Integer team,Integer raceId) {

        Optional<Race> race = raceRepository.findOne(raceId);
        Player player = new Player(name, code, team);
        player = playerRepository.save(player).get();
        race.get().getPlayers().add(player);
        race.get().setNoPlayers(race.get().getPlayers().size());
        raceRepository.update(race.get());
        log.info("Service:Player added");
    }

    public Map<Integer, Player> getPlayersByTeam(Integer teamId) {
        log.info("Service: Finding players by team");
        return playerRepository.getPlayersByTeam(teamId);
    }


    public Optional<Team> getTeamByName(String teamName) {
        return teamRepository.findTeamByName(teamName);
    }

    public Map<Integer, Race> getAllRaces(){
        return raceRepository.findAll();
    }

    public List<Player> getRacePlayersOfTeam(Integer raceId, Integer teamId) {
        Optional<Race> race = raceRepository.findOne(raceId);
        if(race.isPresent()) {
            log.info("Service: Finding players by team");
            return race.get().getPlayers().stream()
                    .filter(p -> p.getTeam().equals(teamId))
                    .toList();
        }
        log.info("Service: Finding players by team no race found");
        return List.of();
    }
}
