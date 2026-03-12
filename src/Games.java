import java.util.ArrayList;
import java.util.Date;

public class Games {

    private String titel;
    private final Date releaseDate;
    private String description;
    private double price;

    public Games(String titel, Date releaseDate) {
        this.titel = titel;
        this.releaseDate = releaseDate;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
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
