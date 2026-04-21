<<<<<<<< HEAD:app/src/main/java/org/example/Entites/User.java
package Entites;

import java.util.ArrayList;

/**
 * Repräsentiert einen Benutzer mit Login-Daten und eigener Spielebibliothek.
 *
 * @author Jannis Lauer (jannis280@outlook.de)
 */
public class User {
========
package org.example.dtos;

import java.util.ArrayList;

public class UserDto {
>>>>>>>> master:app/src/main/java/org/example/dtos/UserDto.java

    private Long id;
    private String username;
    private String email;
    private String password;
<<<<<<<< HEAD:app/src/main/java/org/example/Entites/User.java
    private ArrayList<String> ownedGamesIds;
    private ArrayList<String> ownedDLCsIds;

    /**
     * Erstellt einen neuen Benutzer mit leerer Spiele- und DLC-Liste.
     *
     * @param username Benutzername
     */
    public User(String username) {
        this.username = username;
========
    private String firstname;
    private String lastname;
    private ArrayList<String> ownedGamesIds;
    private ArrayList<String> ownedDlcIds;

    public UserDto() {
>>>>>>>> master:app/src/main/java/org/example/dtos/UserDto.java
        this.ownedGamesIds = new ArrayList<>();
        this.ownedDlcIds = new ArrayList<>();
    }

<<<<<<<< HEAD:app/src/main/java/org/example/Entites/User.java
    /**
     * @return Liste aller Spiel-IDs des Benutzers
     */
    public ArrayList<String> getOwnedGamesIds() {
        return ownedGamesIds;
    }

    /**
     * Setzt die Liste der Spiel-IDs.
     *
     * @param ownedGamesIds neue Spiel-ID-Liste
     */
    public void setOwnedGamesIds(ArrayList<String> ownedGamesIds) {
========
    public UserDto(String username, String email, String password,
                   String firstname, String lastname,
                   ArrayList<String> ownedGamesIds, ArrayList<String> ownedDlcIds) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
>>>>>>>> master:app/src/main/java/org/example/dtos/UserDto.java
        this.ownedGamesIds = ownedGamesIds;
        this.ownedDlcIds = ownedDlcIds;
    }

<<<<<<<< HEAD:app/src/main/java/org/example/Entites/User.java
    /**
     * Fügt eine Spiel-ID zur Bibliothek hinzu.
     *
     * @param gameId Spiel-ID
     */
    public void addOwnedGames(String gameId) {
        this.ownedGamesIds.add(gameId);
    }

    /**
     * @return Liste aller DLC-IDs des Benutzers
     */
    public ArrayList<String> getOwnedDLCsIds() {
        return ownedDLCsIds;
    }

    /**
     * Setzt die Liste der DLC-IDs.
     *
     * @param ownedDLCsIds neue DLC-ID-Liste
     */
    public void setOwnedDLCsIds(ArrayList<String> ownedDLCsIds) {
        this.ownedDLCsIds = ownedDLCsIds;
    }

    /**
     * Fügt eine DLC-ID zur Bibliothek hinzu.
     *
     * @param dlsId DLC-ID
     */
    public void addOwnedDLCs(String dlsId) {
        this.ownedDLCsIds.add(dlsId);
    }

    /**
     * @return Benutzername
     */
========
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

>>>>>>>> master:app/src/main/java/org/example/dtos/UserDto.java
    public String getUsername() {
        return username;
    }

    /**
     * Setzt den Benutzernamen.
     *
     * @param username neuer Benutzername
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return E-Mail-Adresse
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setzt die E-Mail-Adresse.
     *
     * @param email neue E-Mail-Adresse
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return Passwort
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setzt das Passwort.
     *
     * @param password neues Passwort
     */
    public void setPassword(String password) {
        this.password = password;
    }
<<<<<<<< HEAD:app/src/main/java/org/example/Entites/User.java
========

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

    public void addOwnedGameId(String gameId) {
        this.ownedGamesIds.add(gameId);
    }

    public ArrayList<String> getOwnedDlcIds() {
        return ownedDlcIds;
    }

    public void setOwnedDlcIds(ArrayList<String> ownedDlcIds) {
        this.ownedDlcIds = ownedDlcIds;
    }
>>>>>>>> master:app/src/main/java/org/example/dtos/UserDto.java
}
