package org.example.dtos;

import java.util.Date;

public class DlcDto {

    private String id;
    private String dlcName;
    private String gameTitle;
    private String description;
    private double price;
    private Date releaseDate;
    private Date createdAt;

    public DlcDto() { }

    public DlcDto(String id, String dlcName, String gameTitle,
                  String description, double price, Date releaseDate, Date createdAt) {
        this.id = id;
        this.dlcName = dlcName;
        this.gameTitle = gameTitle;
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
