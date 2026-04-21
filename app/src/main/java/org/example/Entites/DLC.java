<<<<<<<< HEAD:app/src/main/java/org/example/Entites/DLC.java
package Entites;

import java.util.Date;

/**
 * Repräsentiert ein herunterladbares Zusatzpaket (DLC) zu einem Spiel.
 *
 * @author Jannis Lauer (jannis280@outlook.de)
 */
public class DLC {
========
package org.example.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "dlcs")
public class Dlc {

    @Id
    @Column(name = "id", nullable = false)
>>>>>>>> master:app/src/main/java/org/example/entities/Dlc.java
    private final String id;

    @Column(name = "dlc_name")
    private String dlcName;

    @Column(name = "game_title")
    private String gameTitle;

    @Column(name = "release_date")
    private Date releaseDate;

    @Column(name = "created_at", nullable = false, updatable = false)
    private final Date createdAt;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private double price;

<<<<<<<< HEAD:app/src/main/java/org/example/Entites/DLC.java
    /**
     * Erstellt ein DLC mit ID und Erstellungszeitpunkt.
     *
     * @param id eindeutige DLC-ID
     */
    public DLC(String id) {
========
    protected Dlc() {
        this.id = null;
        this.createdAt = null;
    }

    public Dlc(String id) {
>>>>>>>> master:app/src/main/java/org/example/entities/Dlc.java
        this.id = id;
        this.createdAt = new Date();
    }

    /**
     * @return eindeutige DLC-ID
     */
    public String getId() {
        return id;
    }

    /**
     * @return Name des DLCs
     */
    public String getDlcName() {
        return dlcName;
    }

    /**
     * Setzt den Namen des DLCs.
     *
     * @param dlcName neuer DLC-Name
     */
    public void setDlcName(String dlcName) {
        this.dlcName = dlcName;
    }

    /**
     * @return Titel des zugehörigen Hauptspiels
     */
    public String getGameTitle() {
        return gameTitle;
    }

    /**
     * Setzt den Titel des zugehörigen Hauptspiels.
     *
     * @param gameTitle Spieltitel
     */
    public void setGameTitle(String gameTitle) {
        this.gameTitle = gameTitle;
    }

    /**
     * @return Veröffentlichungsdatum
     */
    public Date getReleaseDate() {
        return releaseDate;
    }

    /**
     * Setzt das Veröffentlichungsdatum.
     *
     * @param releaseDate neues Veröffentlichungsdatum
     */
    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    /**
     * @return Erstellungszeitpunkt des Datensatzes
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * @return Beschreibung des DLCs
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setzt die Beschreibung des DLCs.
     *
     * @param description neue Beschreibung
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return Preis des DLCs
     */
    public double getPrice() {
        return price;
    }

    /**
     * Setzt den Preis des DLCs.
     *
     * @param price neuer Preis
     */
    public void setPrice(double price) {
        this.price = price;
    }
}
