package network.dto;

import java.io.Serializable;

public class PlayerDTO implements Serializable {
    private String name;
    private String code;
    private String team;
    private String id;

    public PlayerDTO(String name, String code, String team,String id) {
        this.name = name;
        this.code = code;
        this.team = team;
        this.id = id;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "PlayerDTO{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", team=" + team +
                ", id=" + id +
                '}';
    }
}
