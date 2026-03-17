import java.util.ArrayList;
import java.util.Date;

public class Games {

    private final String id;
    private String titel;
    private final Date createdAt;
    private Date releaseDate;
    private String description;
    private double price;

    public Games(String id) {
        this.id = id;
        this.createdAt = new Date(); // Standardmäßig auf das aktuelle Datum setzen
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

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }
}