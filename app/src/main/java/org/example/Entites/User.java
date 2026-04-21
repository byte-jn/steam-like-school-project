package Entites;

import java.util.ArrayList;

/**
 * Repräsentiert einen Benutzer mit Login-Daten und eigener Spielebibliothek.
 *
 * @author Jannis Lauer (jannis280@outlook.de)
 */
public class User {

    private String username;
    private String email;
    private String password;
    private ArrayList<String> ownedGamesIds;
    private ArrayList<String> ownedDLCsIds;

    /**
     * Erstellt einen neuen Benutzer mit leerer Spiele- und DLC-Liste.
     *
     * @param username Benutzername
     */
    public User(String username) {
        setUsername(username);
        this.ownedGamesIds = new ArrayList<>();
        this.ownedDLCsIds = new ArrayList<>();
    }

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
        this.ownedGamesIds = ownedGamesIds;
        System.out.println("All Games for user " + username + " were overwritten with " + ownedGamesIds.toString());
    }

    /**
     * Fügt eine Spiel-ID zur Bibliothek hinzu.
     *
     * @param gameId Spiel-ID
     */
    public void addOwnedGames(String gameId) {
        this.ownedGamesIds.add(gameId);
        System.out.println("Game with id '" + gameId + "' were added to user " + username + "'s library");
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
        System.out.println("All DLCs for user " + username + " were overwritten with " + ownedDLCsIds.toString());
    }

    /**
     * Fügt eine DLC-ID zur Bibliothek hinzu.
     *
     * @param dlsId DLC-ID
     */
    public void addOwnedDLCs(String dlsId) {
        this.ownedDLCsIds.add(dlsId);
        System.out.println("DLC with id '" + dlsId + "' were added to user " + username + "'s library");
    }

    /**
     * @return Benutzername
     */
    public String getUsername() {
        return username;
    }

    /**
     * Setzt den Benutzernamen.
     *
     * @param username neuer Benutzername
     */
    public void setUsername(String username) {
        String oldUsername = this.username;
        this.username = username;
        System.out.println("Username was changed form '" + oldUsername + "' to '" + username + "'");
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
        System.out.println("Email form user " + username + " were set to '" + email + "'");
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
        System.out.println("Password form user " + username + " were set to '" + password + "'");
    }
}
