package model;

import java.util.Objects;

public class Player extends Entity<Integer>{
    private final String name;
    private final String code;
    private final Integer team;


    public Player(String name, String code, Integer team) {
        this.name = name;
        this.code = code;
        this.team = team;
    }

    public String getName() {
        return name;
    }



    public String getCode() {
        return code;
    }

    public Integer getTeam() {
        return team;
    }
    @Override
    public String toString() {
        return "model.Player{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", team=" + team +
                ", id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Player player = (Player) o;
        return Objects.equals(name, player.name) && Objects.equals(code, player.code) && Objects.equals(team, player.team);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, code, team);
    }
}
