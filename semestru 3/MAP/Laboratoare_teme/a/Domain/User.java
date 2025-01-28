package scs.map.Domain;


import java.time.LocalDateTime;
import java.util.Objects;

public class User extends Entity<Long>   {
    String username;
    String password;
    String phoneNumber;
    LocalDateTime joinDate;
    int age;

    public User(String username, String password, String phone_number, LocalDateTime join_date, int age) {
        this.username = username;
        this.password = password;
        this.phoneNumber = phone_number;
        this.joinDate = join_date;
        this.age = age;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phone_number) {
        this.phoneNumber = phone_number;
    }

    public LocalDateTime getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(LocalDateTime join_date) {
        this.joinDate = join_date;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return age == user.age && Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(phoneNumber, user.phoneNumber) && Objects.equals(joinDate, user.joinDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), username, password, phoneNumber, joinDate, age);
    }
}

/*
Metode Avansate de Programare, sectia Informatică 2022–2023, LABORATOR 3 DEADLINE: Săptămâna 4 A.
 Cerințe funționale (– vezi fișierul Enunt Proiect Lab - Retea de socializare.pdf):
  - Add/Remove Utilizator (2p) -
   Add/Remove Prieten (2p)
   - Afisare numar de comunitati (numarul de componente conexe din graful retelei) (0.5p)
   - Afisare cea mai sociabila comunitate (componenta conexa din retea cu cel mai lung drum) (0.5p)
    B. Cerințe non-funționale (vezi seminarul 4): 4p -
     Arhitectură Stratificată 0.5 - DDD (scs.map.Domain Driven Design) 0.25
     - Documentația - Java doc (Tools->Generate Java Doc in InteliJ Idea) 0.5
     - Persistența datelor: în memorie sau in file 1p
     - Validarea datelor - (Strategy Pattern) 0.5
     - Definiți propriile clase de excepții pentru tratarea situațiilor speciale; 0.25
     - Interfața cu utilizatorul, de tip consolă, poate sa fie minimalista,
 */