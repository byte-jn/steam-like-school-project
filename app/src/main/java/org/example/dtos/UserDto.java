package org.example.dtos;

import java.util.ArrayList;

public class UserDto {

    private Long id;
    private String username;
    private String email;
    private String password;
    private String firstname;
    private String lastname;
    private ArrayList<String> ownedGamesIds;
    private ArrayList<String> ownedDlcIds;

    public UserDto() {
        this.ownedGamesIds = new ArrayList<>();
        this.ownedDlcIds = new ArrayList<>();
    }

    public UserDto(String username, String email, String password,
                   String firstname, String lastname,
                   ArrayList<String> ownedGamesIds, ArrayList<String> ownedDlcIds) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.ownedGamesIds = ownedGamesIds;
        this.ownedDlcIds = ownedDlcIds;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public ArrayList<String> getOwnedGamesIds() {
        return ownedGamesIds;
    }

    public void setOwnedGamesIds(ArrayList<String> ownedGamesIds) {
        this.ownedGamesIds = ownedGamesIds;
    }

    public ArrayList<String> getOwnedDlcIds() {
        return ownedDlcIds;
    }

    public void setOwnedDlcIds(ArrayList<String> ownedDlcIds) {
        this.ownedDlcIds = ownedDlcIds;
    }
}
