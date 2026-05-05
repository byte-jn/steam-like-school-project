package Entites;

import java.util.Date;

/**
 * Repräsentiert ein herunterladbares Zusatzpaket (DLC) zu einem Spiel.
 *
 * @author Jannis Lauer (jannis280@outlook.de)
 */
public class DLC {
    private final String id;
    private String dlcName;
    private String gameTitle;
    private Date releaseDate;
    private final Date createdAt;
    private String description;
    private double price;

    /**
     * Erstellt ein DLC mit ID und Erstellungszeitpunkt.
     *
     * @param id eindeutige DLC-ID
     */
    public DLC(String id) {
        this.id = id;
        this.createdAt = new Date();
    }

    /**
     * @return eindeutige DLC-ID
     */
    public String getId() {
        return id;
    }

    /**
     * @return Name des DLCs
     */
    public String getDlcName() {
        return dlcName;
    }

    /**
     * Setzt den Namen des DLCs.
     *
     * @param dlcName neuer DLC-Name
     */
    public void setDlcName(String dlcName) {
        this.dlcName = dlcName;
        System.out.println("Name von Dlc wurde von '" + dlcName + "' auf '" + dlcName + "' gesetzt");
    }

    /**
     * @return Titel des zugehörigen Hauptspiels
     */
    public String getGameTitle() {
        return gameTitle;
    }

    /**
     * Setzt den Titel des zugehörigen Hauptspiels.
     *
     * @param gameTitle Spieltitel
     */
    public void setGameTitle(String gameTitle) {
        this.gameTitle = gameTitle;
        System.out.println("Spiel Titel von Dlc '" + dlcName + "' wurde auf '" + gameTitle + "' gesetzt");
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
        System.out.println("Erscheinungsdatum von Dlc '" + dlcName + "' wurde auf '" + releaseDate.toString() + "' gesetzt");
    }

    /**
     * @return Erstellungszeitpunkt des Datensatzes
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * @return Beschreibung des DLCs
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setzt die Beschreibung des DLCs.
     *
     * @param description neue Beschreibung
     */
    public void setDescription(String description) {
        this.description = description;
        System.out.println("Beschreibung von Dlc '" + dlcName + "' wurde auf '" + description + "' gesetzt");
    }

    /**
     * @return Preis des DLCs
     */
    public double getPrice() {
        return price;
    }

    /**
     * Setzt den Preis des DLCs.
     *
     * @param price neuer Preis
     */
    public void setPrice(double price) {
        this.price = price;
        System.out.println("Preis von Dlc '" + dlcName + "' wurde auf " + price + "€ gesetzt");
    }
}
