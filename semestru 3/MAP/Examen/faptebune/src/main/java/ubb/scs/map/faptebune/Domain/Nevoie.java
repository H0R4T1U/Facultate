package ubb.scs.map.faptebune.Domain;

import java.time.LocalDateTime;

public class Nevoie extends Entity {
    private String title;
    private String descriere;
    private LocalDateTime deadline;
    private Long omInNevoie;
    private Long omSalvator;
    private String status;

    public Nevoie( String title, String descriere, LocalDateTime deadline, Long omInNevoie, Long omSalvator, String status) {
        this.title = title;
        this.descriere = descriere;
        this.deadline = deadline;
        this.omInNevoie = omInNevoie;
        this.omSalvator = omSalvator;
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public Long getOmInNevoie() {
        return omInNevoie;
    }

    public void setOmInNevoie(Long omInNevoie) {
        this.omInNevoie = omInNevoie;
    }

    public Long getOmSalvator() {
        return omSalvator;
    }

    public void setOmSalvator(Long omSalvator) {
        this.omSalvator = omSalvator;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
