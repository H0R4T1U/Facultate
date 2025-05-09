package server;


import Services.IObserver;
import Services.IService;
import Services.MotoException;
import model.Player;
import model.Race;
import model.Team;
import model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repository.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.Thread.sleep;

public class Service implements IService {
    private static final Logger log = LogManager.getLogger(Service.class);
    private Map<Integer, IObserver> loggedClients;
    private final PlayerRepository playerRepository;
    private final UserRepository userRepository;
    private final RaceRepository raceRepository;
    private final TeamRepository teamRepository;
    private final int defaultThreadsNo=2;

    public Service(PlayerRepository playerRepository, UserRepository userRepository, RaceRepository raceRepository, TeamRepository teamRepository) {
        this.playerRepository = playerRepository;
        this.userRepository = userRepository;
        this.raceRepository = raceRepository;
        this.teamRepository = teamRepository;
        loggedClients=new ConcurrentHashMap<>();;
    }


    public synchronized void login(User user, IObserver client) throws MotoException {
        Optional<User> usr=userRepository.findByUsername(user.getUsername(),user.getPassword());
        if(usr.isEmpty()){
            throw new MotoException("Username or Password invalid");
        }
        User userR = usr.get();
        if (loggedClients.get(userR.getId()) != null)
            throw new MotoException("User already logged in.");
        loggedClients.put(userR.getId(), client);
    }

    public void logout(User user,IObserver client)  {
        User usr = userRepository.findByUsername(user.getUsername(), TextUtils.simpleDecode(user.getPassword())).get();
        loggedClients.remove(usr.getId());
        log.info("User logged out: " + user.getUsername());
    }

    public synchronized void add(Player player,Integer raceId,IObserver client) throws MotoException {
        try {
            Optional<Race> race = raceRepository.findOne(raceId);
            player = playerRepository.save(player).get();
            race.get().getPlayers().add(player);
            race.get().setNoPlayers(race.get().getPlayers().size());
            raceRepository.update(race.get());
            log.info("Service:Player added");
            for (IObserver c : loggedClients.values()) {
                    try {
                        c.playerAdded(player);
                    } catch (MotoException e) {
                        log.error("Error notifying player added " + e);
                    }
            }
        }catch (Exception e){
            throw new MotoException(e.getMessage());
        }
    }

    public Map<Integer, Player> getPlayersByTeam(Integer teamId) {
        log.info("Service: Finding players by team");
        return playerRepository.getPlayersByTeam(teamId);
    }


    public Optional<Team> getTeamByName(String teamName) {
        return teamRepository.findTeamByName(teamName);
    }

    public synchronized Race[] getAllRaces(){
        return raceRepository.findAll().values().toArray(new Race[0]);
    }

    public synchronized Player[] getRacePlayersOfTeam(Integer raceId, Integer teamId) {
        Optional<Race> race = raceRepository.findOne(raceId);
        if(race.isPresent()) {
            log.info("Service: Finding players by team");
            return race.get().getPlayers().stream()
                    .filter(p -> p.getTeam().equals(teamId))
                    .toList().toArray(new Player[0]);
        }
        log.info("Service: Finding players by team no race found");
        return new Player[0];
    }

private void notifyPlayerAdded(Player player) {
    ExecutorService executor = Executors.newFixedThreadPool(defaultThreadsNo);
    for (IObserver client : loggedClients.values()) {
        executor.execute(() -> {
            try {
                client.playerAdded(player);
            } catch (MotoException e) {
                log.error("Error notifying player added " + e);
            }
        });
    }
    executor.shutdown();
    }
}