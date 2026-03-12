import java.util.Date;

public class DLC {
    private final String dlcName;
    private final String gameTitle;
    private final Date releaseDate;
    private String description;
    private double price;

    public DLC(String dlcName, String gameTitle, Date releaseDate, String description, double price) {
        this.dlcName = dlcName;
        this.gameTitle = gameTitle;
        this.releaseDate = releaseDate;
    }

    public String getDlcName() {
        return dlcName;
    }

    public String getGameTitle() {
        return gameTitle;
    }

    public Date getReleaseDate() {
        return releaseDate;
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
