import java.util.Date;

public class DLC {
    private final String id;
    private String dlcName;
    private String gameTitle;
    private Date releaseDate;
    private final Date createdAt;
    private String description;
    private double price;

    public DLC(String id) {
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
