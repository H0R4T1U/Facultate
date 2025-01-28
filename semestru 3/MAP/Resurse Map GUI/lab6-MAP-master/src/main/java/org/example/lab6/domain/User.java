package org.example.lab6.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User extends Entity<Long> {

    private final String firstName;
    private final String lastName;
    private final String username;
    private final String password;
    private List<User> friends;


    public User(String firstName, String lastName, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.friends = new ArrayList<>();
    }

    public User(Long id, String firstName, String lastName, String username, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.friends = new ArrayList<>();
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() { return firstName + " " + lastName; }

    public String getUsername() { return username; }

    public String getPassword() { return password; }

    public List<User> getFriends() {
        return friends;
    }

    public void addFriend(User u) {
        friends.add(u);
    }

    public void removeFriend(User u) {
        friends.remove(u);
    }

    @Override
    public String toString() {
        return username + " " + firstName + " " + lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User that)) return false;
        return getFirstName().equals(that.getFirstName()) &&
                getLastName().equals(that.getLastName()) &&
                getFriends().equals(that.getFriends()) &&
                getUsername().equals(that.getUsername());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirstName(), getLastName(), getUsername(), getFriends());
    }

    public void setFriends(List<User> friendsList) {
        this.friends = friendsList;
    }
}


