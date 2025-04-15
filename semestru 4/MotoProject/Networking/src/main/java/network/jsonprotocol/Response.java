package network.jsonprotocol;

import network.dto.PlayerDTO;
import network.dto.RaceDTO;
import network.dto.TeamDTO;
import network.dto.UserDTO;

import java.io.Serializable;
import java.util.Arrays;


public class Response implements Serializable {
    private ResponseType type;
    private String errorMessage;
    private UserDTO user;
    private PlayerDTO[] players;
    private TeamDTO team;
    private RaceDTO[] races;
    private PlayerDTO newPlayer;
    public Response() {
    }

    public ResponseType getType() {
        return type;
    }

    public void setType(ResponseType type) {
        this.type = type;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public PlayerDTO[] getPlayers() {
        return players;
    }

    public void setPlayers(PlayerDTO[] players) {
        this.players = players;
    }

    public TeamDTO getTeam() {
        return team;
    }

    public void setTeam(TeamDTO team) {
        this.team = team;
    }

    public RaceDTO[] getRaces() {
        return races;
    }

    public void setRaces(RaceDTO[] races) {
        this.races = races;
    }

    public PlayerDTO getNewPlayer() {
        return newPlayer;
    }

    public void setNewPlayer(PlayerDTO new_player) {
        this.newPlayer = new_player;
    }

    @Override
    public String toString() {
        return "Response{" +
                "type=" + type +
                ", errorMessage='" + errorMessage + '\'' +
                ", user=" + user +
                ", players=" + Arrays.toString(players) +
                ", team=" + team +
                ", races=" + Arrays.toString(races) +
                ", newPlayer=" + newPlayer +
                '}';
    }
}
