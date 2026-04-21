<<<<<<<< HEAD:app/src/main/java/org/example/Entites/Games.java
package Entites;

import java.util.Date;

/**
 * Repräsentiert ein Spiel im Store.
 *
 * @author Jannis Lauer (jannis280@outlook.de)
 */
========
package org.example.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "games")
>>>>>>>> master:app/src/main/java/org/example/entities/Games.java
public class Games {

    @Id
    @Column(name = "id", nullable = false)
    private final String id;

    @Column(name = "titel")
    private String titel;

    @Column(name = "created_at", nullable = false, updatable = false)
    private final Date createdAt;

    @Column(name = "release_date")
    private Date releaseDate;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private double price;

<<<<<<<< HEAD:app/src/main/java/org/example/Entites/Games.java
    /**
     * Erstellt ein Spiel mit ID und Zeitstempel der Erstellung.
     *
     * @param id eindeutige Spiel-ID
     */
========
    protected Games() {
        this.id = null;
        this.createdAt = null;
    }

>>>>>>>> master:app/src/main/java/org/example/entities/Games.java
    public Games(String id) {
        this.id = id;
        this.createdAt = new Date();
    }

    /**
     * @return eindeutige Spiel-ID
     */
    public String getId() {
        return id;
    }

    /**
     * @return Erstellungszeitpunkt des Datensatzes
     */
    public Date getCreatedAt() {
        return createdAt;
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
     * @return Beschreibung des Spiels
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setzt die Beschreibung des Spiels.
     *
     * @param description neue Beschreibung
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return Preis des Spiels
     */
    public double getPrice() {
        return price;
    }

    /**
     * Setzt den Preis des Spiels.
     *
     * @param price neuer Preis
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return Titel des Spiels
     */
    public String getTitel() {
        return titel;
    }

    /**
     * Setzt den Titel des Spiels.
     *
     * @param titel neuer Titel
     */
    public void setTitel(String titel) {
        this.titel = titel;
    }
}
