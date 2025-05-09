
import Services.MotoException;
import io.grpc.stub.StreamObserver;
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
import services.grpc.MotoServiceGrpc;
import services.grpc.MotoServiceProto;

import java.util.*;

public class MotoServiceImpl extends MotoServiceGrpc.MotoServiceImplBase {
    private static final Logger log = LogManager.getLogger(MotoServiceImpl.class);
    private final PlayerRepository playerRepository;
    private final UserRepository userRepository;
    private final RaceRepository raceRepository;
    private final TeamRepository teamRepository;
    private final int defaultThreadsNo = 2;
    private final Map<Integer,StreamObserver<MotoServiceProto.Player>> observers = Collections.synchronizedMap(new HashMap<>());

    public MotoServiceImpl(PlayerRepository playerRepository, UserRepository userRepository, RaceRepository raceRepository, TeamRepository teamRepository) {
        this.playerRepository = playerRepository;
        this.userRepository = userRepository;
        this.raceRepository = raceRepository;
        this.teamRepository = teamRepository;
    }

    @Override
    public void login(MotoServiceProto.LoginRequest request, StreamObserver<MotoServiceProto.LoginResponse> responseObserver) {
        User usr = new User(request.getUser().getUsername(), request.getUser().getPassword());
        Optional<User> user = userRepository.findByUsername(usr.getUsername(), usr.getPassword());
        if (user.isEmpty()) {
            responseObserver.onError(new MotoException("Username or Password invalid"));
            return;
        }
        if(observers.containsKey(user.get().getId())) {
            responseObserver.onError(new MotoException("User already logged in."));
            return;
        }
        MotoServiceProto.LoginResponse response = MotoServiceProto.LoginResponse.newBuilder()
                .setMessage("Login successful")
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void subscribeToPlayerUpdates(MotoServiceProto.User request, StreamObserver<MotoServiceProto.Player> responseObserver) {
        synchronized (observers) {
            User u = userRepository.findByUsername(request.getUsername(),request.getPassword()).get();
            observers.put(u.getId(),responseObserver);
        }

    }

    @Override
    public void logout(MotoServiceProto.LoginRequest request, StreamObserver<MotoServiceProto.LoginResponse> responseObserver) {
        User user = userRepository.findByUsername(request.getUser().getUsername(),request.getUser().getPassword()).get();
        observers.remove(user.getId());
        MotoServiceProto.LoginResponse response = MotoServiceProto.LoginResponse.newBuilder()
                .setMessage("Logout successful")
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();

    }

    @Override
    public void getAllRaces(MotoServiceProto.Empty request, StreamObserver<MotoServiceProto.Race> responseObserver) {
        Race[] races = raceRepository.findAll().values().toArray(Race[]::new);
        for (Race race : races) {
            MotoServiceProto.Race response = MotoServiceProto.Race.newBuilder()
                    .setId(race.getId())
                    .setEngineType(race.getEngineType())
                    .setNoPlayers(race.getNoPlayers())
                    .addAllPlayers(race.getPlayers().stream().map(player ->
                            MotoServiceProto.Player.newBuilder()
                                    .setId(player.getId())
                                    .setName(player.getName())
                                    .setCode(player.getCode())
                                    .setTeam(player.getTeam())
                                    .build()).toList())
                    .build();
            responseObserver.onNext(response);
        }
        responseObserver.onCompleted();
    }

    @Override
    public void getTeamByName(MotoServiceProto.TeamRequest request, StreamObserver<MotoServiceProto.Team> responseObserver) {
        Optional<Team> team = teamRepository.findTeamByName(request.getName());
        if (team.isPresent()) {
            MotoServiceProto.Team response = MotoServiceProto.Team.newBuilder()
                    .setId(team.get().getId())
                    .setName(team.get().getName())
                    .build();
            responseObserver.onNext(response);
        } else {
            responseObserver.onError(new MotoException("Team not found"));
        }
        responseObserver.onCompleted();
    }

    @Override
    public void getRacePlayersOfTeam(MotoServiceProto.RacePlayersRequest request, StreamObserver<MotoServiceProto.RacePlayersResponse> responseObserver) {

        Optional<Race> race = raceRepository.findOne(request.getRaceId());
        if (race.isPresent()) {
            log.info("Service: Finding players by team");
            Player[] players = race.get().getPlayers().stream()
                    .filter(p -> p.getTeam().equals(request.getTeamId()))
                    .toList().toArray(new Player[0]);

            log.info("Service: Finding players by team no race found");

            MotoServiceProto.RacePlayersResponse response = MotoServiceProto.RacePlayersResponse.newBuilder()
                    .addAllPlayers(java.util.Arrays.stream(players).map(player ->
                            MotoServiceProto.Player.newBuilder()
                                    .setId(player.getId())
                                    .setName(player.getName())
                                    .setCode(player.getCode())
                                    .setTeam(player.getTeam())
                                    .build()).toList())
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
    }

    @Override
    public void addPlayer(MotoServiceProto.AddPlayerRequest request, StreamObserver<MotoServiceProto.Empty> responseObserver) {
        Player player = new Player(request.getPlayer().getName(), request.getPlayer().getCode(), request.getPlayer().getTeam());
        player.setId(request.getPlayer().getId());
        playerRepository.save(player);
        raceRepository.findOne(request.getRaceId()).ifPresent(race -> {
            race.getPlayers().add(player);
            race.setNoPlayers(race.getNoPlayers() + 1);
            raceRepository.update(race);
        });
        // Notify all observers
        MotoServiceProto.Player protoPlayer = request.getPlayer(); // Already in proto form
        synchronized (observers) {
            Iterator<StreamObserver<MotoServiceProto.Player>> iter = observers.values().iterator();
            while (iter.hasNext()) {
                try {
                    iter.next().onNext(protoPlayer);
                } catch (Exception e) {
                    iter.remove(); // Clean up dead observers
                    log.warn("Removed broken observer");
                }
            }

            responseObserver.onNext(MotoServiceProto.Empty.newBuilder().build());
            responseObserver.onCompleted();
        }

    }
}





