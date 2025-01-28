package ubb.scs.map.faptebune.Domain;

public class Entity {
    protected Long SerialVersionUID;
    protected Long id;

    public Long getSerialVersionUID() {
        return SerialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long serialVersionUID) {
        SerialVersionUID = serialVersionUID;
        id=serialVersionUID;
    }
}
