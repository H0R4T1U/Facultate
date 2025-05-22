package testing.motorest.repository;




import testing.motorest.model.Player;

import java.util.Map;

public interface PlayerRepository extends Repository<Integer, Player> {
    Map<Integer, Player> getPlayersByTeam(Integer teamId);
}
