package network.jsonprotocol;


import network.dto.PlayerDTO;
import network.dto.UserDTO;

import java.util.Arrays;

public class Request {
    private RequestType type;
    private UserDTO user;
    private PlayerDTO player;
    private String raceId;
    private String teamName;
    private String teamId;

    public Request(){}
    public RequestType getType() {
        return type;
    }

    public void setType(RequestType type) {
        this.type = type;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public PlayerDTO getPlayer() {
        return player;
    }

    public void setPlayer(PlayerDTO player) {
        this.player = player;
    }
    public String getRaceId() {
        return raceId;
    }

    public void setRaceId(String raceId) {
        this.raceId = raceId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    @Override
    public String toString() {
        return "Request{" +
                "type=" + type +
                ", user=" + user +
                ", player=" + player +
                ", raceId=" + raceId +
                ", teamName='" + teamName + '\'' +
                ", teamId=" + teamId +
                '}';
    }
}
