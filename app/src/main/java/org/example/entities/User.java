package org.example.entities;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @ElementCollection
    @CollectionTable(name = "user_owned_games", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "game_id")
    private List<String> ownedGamesIds;

    @ElementCollection
    @CollectionTable(name = "user_owned_dlcs", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "dlc_id")
    private List<String> ownedDLCsIds;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    protected User() { }

    public User(String firstName, String lastName) {
        firstname = firstName;
        lastname = lastName;
        this.ownedGamesIds = new ArrayList<>();
        this.ownedDLCsIds = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public List<String> getOwnedGamesIds() {
        return ownedGamesIds;
    }

    public void setOwnedGamesIds(List<String> ownedGamesIds) {
        this.ownedGamesIds = ownedGamesIds;
    }

    public void addOwnedGames(String gameId) {
        this.ownedGamesIds.add(gameId);
    }

    public List<String> getOwnedDLCsIds() {
        return ownedDLCsIds;
    }

    public void setOwnedDLCsIds(List<String> ownedDLCsIds) {
        this.ownedDLCsIds = ownedDLCsIds;
    }

    public void addOwnedDLCs(String dlsId) {
        this.ownedDLCsIds.add(dlsId);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname1) {
        firstname = firstname1;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
