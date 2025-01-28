package ubb.scs.map.Domain;


import ubb.scs.map.Utils.Constants;

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
    public String toString() {
        return username + " age:" + age + " joined:" + joinDate.format(Constants.DATE_FORMATTER);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), username, password, phoneNumber, joinDate, age);
    }
}
