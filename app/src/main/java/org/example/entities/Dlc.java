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

    protected Dlc() {
        this.id = null;
        this.createdAt = null;
    }

    public Dlc(String id) {
        this.id = id;
        this.createdAt = new Date();
    }

    public String getId() {
        return id;
    }

    public String getDlcName() {
        return dlcName;
    }

    public void setDlcName(String dlcName) {
        this.dlcName = dlcName;
    }

    public String getGameTitle() {
        return gameTitle;
    }

    public void setGameTitle(String gameTitle) {
        this.gameTitle = gameTitle;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
