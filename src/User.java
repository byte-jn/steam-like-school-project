import java.util.ArrayList;

public class User {

    private final String username;
    private ArrayList<Games> ownedGames;
    private ArrayList<DLC> ownedDLCs;
    private String vorname;
    private String nachname;

    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public ArrayList<Games> getOwnedGames() {
        return ownedGames;
    }

    public void setOwnedGames(ArrayList<Games> ownedGames) {
        this.ownedGames = ownedGames;
    }

    public void addOwnedGames(Games game) {
        this.ownedGames.add(game);
    }

    public ArrayList<DLC> getOwnedDLCs() {
        return ownedDLCs;
    }

    public void setOwnedDLCs(ArrayList<DLC> ownedDLCs) {
        this.ownedDLCs = ownedDLCs;
    }

    public void addOwnedDLCs(DLC dLCs) {
        this.ownedDLCs.add(dLCs);
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        if (vorname == null || vorname.trim().isEmpty()) {
            return;
        }
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        if (nachname == null || nachname.trim().isEmpty()) {
            return;
        }
        this.nachname = nachname;
    }
}
