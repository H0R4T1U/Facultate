package project.moto;

public interface Identifiable<ID> {
    void setId(ID id);
    ID getId();
}
