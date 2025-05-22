package model;

import java.util.Objects;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

@Entity
@Table(name = "Player")
public class Player implements model.Entity<Integer>{

    private Integer id;
    private String name;
    private String code;
    private Integer team;

    public Player() {
        this.name = "";
        this.code = "";
        this.team = 0;
    }
    public Player(String name, String code, Integer team) {
        this.name = name;
        this.code = code;
        this.team = team;
    }

    @Column(name = "Name")
    public String getName() {
        return name;
    }

    @Column(name = "Code")
    public String getCode() {
        return code;
    }
    @Column(name = "Team")
    public Integer getTeam() {
        return team;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setTeam(Integer team) {
        this.team = team;
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

    @Override
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer integer) {
        this.id = integer;
    }
}
