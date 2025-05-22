package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.*;
import jakarta.persistence.Entity;

@Entity
@Table(name = "Race")
public class Race implements model.Entity<Integer> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;
    @Column(name = "EngineType")
    private Integer engineType;
    @Column(name = "NoPlayers")
    private Integer noPlayers;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "PlayerRaces",
            joinColumns = @JoinColumn(name = "RaceId"),
            inverseJoinColumns = @JoinColumn(name = "PlayerId")
    )
    private List<Player> players;

    public Race() {
        this.engineType =0;
        this.noPlayers = 0;
        this.players = new ArrayList<>();

    }

    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public void setId(Integer integer) {
        this.id = integer;
    }

    public Race(Integer engineType) {
        this.engineType = engineType;
        this.noPlayers = 0;
        this.players = new ArrayList<>();
    }

    public Integer getEngineType() {
        return engineType;
    }

    public Integer getNoPlayers() {
        return noPlayers;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setNoPlayers(Integer noPlayers) {
        this.noPlayers = noPlayers;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public void setEngineType(Integer engineType) {
        this.engineType = engineType;
    }

    @Override
    public String toString() {
        return "model.Race{" +
                "engineType=" + engineType +
                ", noPlayers=" + noPlayers +
                ", playerIds=" + players +
                ", id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Race race = (Race) o;
        return Objects.equals(engineType, race.engineType) && Objects.equals(noPlayers, race.noPlayers) && Objects.equals(players, race.players);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), engineType, noPlayers, players);
    }
}