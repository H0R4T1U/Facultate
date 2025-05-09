package Persistence.src.main.repository;



import model.Player;

import java.util.Map;

public interface PlayerRepository extends Repository<Integer, Player> {
    Map<Integer, Player> getPlayersByTeam(Integer teamId);
}
