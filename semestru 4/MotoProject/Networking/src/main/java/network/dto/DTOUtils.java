package network.dto;


import model.Player;
import model.Race;
import model.Team;
import model.User;

import java.util.Optional;

public class DTOUtils {
    public static User getFromDTO(UserDTO usdto){
        String username=usdto.getUsername();
        String pass=usdto.getPasswd();

        return new User(username,pass);

    }
    public static User[] getFromDTO(UserDTO[] users){
        User[] friends=new User[users.length];
        for(int i=0;i<users.length;i++){
            friends[i]=getFromDTO(users[i]);
        }
        return friends;
    }
    public static UserDTO getDTO(User user){
        String username=user.getUsername();
        String pass=user.getPassword();
        return new UserDTO(username, pass);
    }
    public static UserDTO[] getDTO(User[] users){
        UserDTO[] frDTO=new UserDTO[users.length];
        for(int i=0;i<users.length;i++)
            frDTO[i]=getDTO(users[i]);
        return frDTO;
    }


    public static PlayerDTO getDTO(Player player){
        String name=player.getName();
        String code=player.getCode();
        String team = player.getTeam().toString();
        return new PlayerDTO(name, code,team);
    }
    public static PlayerDTO[] getDTO(Player[] players) {
        PlayerDTO[] pDTO=new PlayerDTO[players.length];
        for(int i=0;i<players.length;i++)
            pDTO[i]=getDTO(players[i]);
        return pDTO;
    }
    public static Player getFromDTO(PlayerDTO playerDTO){
        String name=playerDTO.getName();
        String code=playerDTO.getCode();
        Integer team=Integer.parseInt(playerDTO.getTeam());
        return new Player(name, code, team);
    }
    public static Player[] getFromDTO(PlayerDTO[] players) {
        Player[] pDTO=new Player[players.length];
        for(int i=0;i<players.length;i++)
            pDTO[i]=getFromDTO(players[i]);
        return pDTO;
    }
    public static RaceDTO getDTO(Race race){
        RaceDTO raceDTO=new RaceDTO();
        raceDTO.setId(String.valueOf(race.getId()));
        raceDTO.setEngineType(String.valueOf(race.getEngineType()));
        raceDTO.setNoPlayers(String.valueOf(race.getNoPlayers()));
        return raceDTO;
    }
    public static RaceDTO[] getDTO(Race[] races) {
        RaceDTO[] rDTO=new RaceDTO[races.length];
        for(int i=0;i<races.length;i++)
            rDTO[i]=getDTO(races[i]);
        return rDTO;
    }

    public static Race getFromDTO(RaceDTO raceDTO){
        Integer engineType=Integer.parseInt(raceDTO.getEngineType());
        Integer noPlayers=Integer.parseInt(raceDTO.getNoPlayers());
        Integer id=Integer.parseInt(raceDTO.getId());
        Race race = new Race(engineType);
        race.setId(id);
        race.setNoPlayers(noPlayers);
        return race;

    }
    public static Race[] getFromDTO(RaceDTO[] races) {
        Race[] rDTO=new Race[races.length];
        for(int i =0; i <races.length;i++){
            rDTO[i]=getFromDTO(races[i]);
        }
        return rDTO;
    }



    public static Team getFromDTO(TeamDTO team) {
        Team t = new Team(team.getName());
        t.setId(Integer.valueOf(team.getId()));
        return t;
    }

    public static TeamDTO getDTO(Team team) {
        TeamDTO tDTO = new TeamDTO(team.getName());
        tDTO.setId(String.valueOf(team.getId()));
        return tDTO;
    }

}
