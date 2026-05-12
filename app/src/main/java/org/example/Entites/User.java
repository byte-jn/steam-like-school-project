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
     * Erstellt einen neuen Benutzer mit leerer Spiele- und DLC-Liste.
     *
     * @param username Benutzername
     */
    public User(String username, String email, String password) {
        setUsername(username);
        setEmail(email);
        setPassword(password);
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
        System.out.println("Alle DLCs von Benutzer " + username + " wurden auf " + ownedGamesIds.toString() + "' gesetzt");
    }

    /**
     * Fügt eine Spiel-ID zur Bibliothek hinzu.
     *
     * @param gameId Spiel-ID
     */
    public void addOwnedGames(String gameId) {
        this.ownedGamesIds.add(gameId);
        System.out.println("Spiel mit id '" + gameId + "' wurden zu Benutzer " + username + "'s Bibliothek hinzugefügt");
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
        System.out.println("Alle DLCs von Benutzer " + username + " wurden auf '" + ownedDLCsIds.toString() + "' gesetzt");
    }

    /**
     * Fügt eine DLC-ID zur Bibliothek hinzu.
     *
     * @param dlsId DLC-ID
     */
    public void addOwnedDLCs(String dlsId) {
        this.ownedDLCsIds.add(dlsId);
        System.out.println("DLC mit id '" + dlsId + "' wurde zu Benutzer " + username + "'s Bibliothek hinzugefügt");
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
        System.out.println("Benutzername wurde von '" + oldUsername + "' zu '" + username + "' geändert");
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
        System.out.println("Email von Benutzer " + username + " wurde auf '" + email + "' gesetzt");
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
        System.out.println("Passwort für Benutzer " + username + " wurde auf '" + password + "' gesetzt");
    }

    public void removeGame(String id) {
        if (this.ownedGamesIds.remove(id)) {
            System.out.println("Spiel mit id '" + id + "' wurde zu Benutzer " + username + "'s Bibliothek entfernt");
        } else {
            System.out.println("Spiel mit id '" + id + "' wurde nicht in Benutzers " + username + "'s Bibliothek gefunden");
        }
    }

    public void removeDlc(String id) {
        if (this.ownedDLCsIds.remove(id)) {
            System.out.println("DLC mit id '" + id + "' wurde zu Benutzer " + username + "'s Bibliothek entfernt");
        } else {
            System.out.println("DLC mit id '" + id + "'  wurde nicht zu Benutzer " + username + "'s Bibliothek gefunden");
        }
    }
}
