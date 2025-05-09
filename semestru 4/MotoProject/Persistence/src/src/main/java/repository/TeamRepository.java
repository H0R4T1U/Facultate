package Persistence.src.main.java.repository;

import model.Team;
import java.util.Optional;


public interface TeamRepository extends Repository<Integer,Team> {
    Optional<Team>  findTeamByName(String name);

}
