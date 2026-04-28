package org.example.dtos;

import java.util.Date;

public class GameDto {

    private String id;
    private String titel;
    private String description;
    private double price;
    private Date releaseDate;
    private Date createdAt;

    public GameDto() { }

    public GameDto(String id, String titel, String description,
                   double price, Date releaseDate, Date createdAt) {
        this.id = id;
        this.titel = titel;
        this.description = description;
        this.price = price;
        this.releaseDate = releaseDate;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitle(String titel) {
        this.titel = titel;
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

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
