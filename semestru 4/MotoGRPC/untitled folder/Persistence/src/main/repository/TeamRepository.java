package Persistence.src.main.repository;

import model.Team;
import java.util.Optional;


public interface TeamRepository extends Repository<Integer,Team> {
    Optional<Team>  findTeamByName(String name);

}
