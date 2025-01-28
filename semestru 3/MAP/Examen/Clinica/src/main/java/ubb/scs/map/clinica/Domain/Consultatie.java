package ubb.scs.map.clinica.Domain;

import java.time.LocalDate;
import java.time.LocalTime;

public class Consultatie {
    private Long id;
    private Long idMedic;
    private String cnp;
    private String nume;
    private LocalDate Data;
    private LocalTime ora;

    public Consultatie(Long idMedic, String cnp, String nume, LocalDate data, LocalTime ora) {
        this.idMedic = idMedic;
        this.cnp = cnp;
        this.nume = nume;
        Data = data;
        this.ora = ora;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdMedic() {
        return idMedic;
    }

    public void setIdMedic(Long idMedic) {
        this.idMedic = idMedic;
    }

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public LocalDate getData() {
        return Data;
    }

    public void setData(LocalDate data) {
        Data = data;
    }

    public LocalTime getOra() {
        return ora;
    }

    public void setOra(LocalTime ora) {
        this.ora = ora;
    }
}
