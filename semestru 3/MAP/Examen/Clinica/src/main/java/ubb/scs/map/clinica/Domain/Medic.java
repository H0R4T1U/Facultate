package ubb.scs.map.clinica.Domain;

public class Medic {
    private Long id;
    private Long idSectie;
    private String nume;
    private Integer vechime;
    private boolean rezident;

    public Medic(Long idSectie, String nume, Integer vechime, boolean rezident) {
        this.idSectie = idSectie;
        this.nume = nume;
        this.vechime = vechime;
        this.rezident = rezident;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return this.nume;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdSectie() {
        return idSectie;
    }

    public void setIdSectie(Long idSectie) {
        this.idSectie = idSectie;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public Integer getVechime() {
        return vechime;
    }

    public void setVechime(Integer vechime) {
        this.vechime = vechime;
    }

    public boolean isRezident() {
        return rezident;
    }

    public void setRezident(boolean rezident) {
        this.rezident = rezident;
    }
}
