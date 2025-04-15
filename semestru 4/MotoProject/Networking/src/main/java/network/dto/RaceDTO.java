package network.dto;

import java.io.Serializable;

public class RaceDTO implements Serializable {
    private String noPlayers;
    private String engineType;
    private String id;
    public String getNoPlayers() {
        return noPlayers;
    }

    public void setNoPlayers(String noPlayers) {
        this.noPlayers = noPlayers;
    }

    public String getEngineType() {
        return engineType;
    }

    public void setEngineType(String engineType) {
        this.engineType = engineType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "RaceDTO{" +
                "noPlayers=" + noPlayers +
                ", engineType=" + engineType +
                ", id=" + id +
                '}';
    }
}
