import java.util.Date;

/**
 * Repräsentiert ein Spiel im Store.
 *
 * @author Jannis Lauer (jannis280@outlook.de)
 */
public class Games {

    private final String id;
    private String titel;
    private final Date createdAt;
    private Date releaseDate;
    private String description;
    private double price;

    /**
     * Erstellt ein Spiel mit ID und Zeitstempel der Erstellung.
     *
     * @param id eindeutige Spiel-ID
     */
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