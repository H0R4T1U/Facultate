package network.dto;

import java.io.Serializable;

public class TeamDTO implements Serializable {
    private String name;
    private String Id;

    public TeamDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    @Override
    public String toString() {
        return "TeamsDTO{" +
                "name='" + name + '\'' +
                ", Id='" + Id + '\'' +
                '}';
    }
}
