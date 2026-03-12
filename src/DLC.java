import java.util.Date;

public class DLC {
    private String dlcName;
    private final String gameTitle;
    private final Date releaseDate;
    private String description;
    private double price;

    public DLC(String dlcName, String gameTitle, Date releaseDate, String description, double price) {
        this.dlcName = dlcName;
        this.gameTitle = gameTitle;
        this.releaseDate = releaseDate;
        this.description = description;
        this.price = price;
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

    public double getPrice() {
        return price;
    }

    public void setDlcName(String dlcName) {
        this.dlcName = dlcName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
