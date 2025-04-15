package network.dto;

import java.io.Serializable;

public class PlayerDTO implements Serializable {
    private String name;
    private String code;
    private String team;

    public PlayerDTO(String name, String code, String team) {
        this.name = name;
        this.code = code;
        this.team = team;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        code = code;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    @Override
    public String toString() {
        return "PlayerDTO{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", team=" + team +
                '}';
    }
}
