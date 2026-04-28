package org.example.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "games")
public class Game {
    @Id
    @Column(name = "id", nullable = false)
    private final String id;

    @Column(name = "title")
    private String title;

    @Column(name = "created_at", nullable = false, updatable = false)
    private final Date createdAt;

    @Column(name = "release_date")
    private Date releaseDate;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private double price;

    protected Game() {
        this.id = null;
        this.createdAt = null;
    }

    public Game(String id) {
        this.id = id;
        this.createdAt = new Date();
    }

    public String getId() {
        return id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
