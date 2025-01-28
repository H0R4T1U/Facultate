package ubb.scs.map.clinica.Domain;

public class Sectie {
    private Long id;
    private String nume;
    private Long idSef;
    private Integer pretConsult;
    private Integer durataConsult;

    public Sectie(String nume, Long idSef, Integer pretConsult, Integer durataConsult) {
        this.nume = nume;
        this.idSef = idSef;
        this.pretConsult = pretConsult;
        this.durataConsult = durataConsult;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return nume;

    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public Long getIdSef() {
        return idSef;
    }

    public void setIdSef(Long idSef) {
        this.idSef = idSef;
    }

    public Integer getPretConsult() {
        return pretConsult;
    }

    public void setPretConsult(Integer pretConsult) {
        this.pretConsult = pretConsult;
    }

    public Integer getDurataConsult() {
        return durataConsult;
    }

    public void setDurataConsult(Integer durataConsult) {
        this.durataConsult = durataConsult;
    }
}
