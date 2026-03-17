import java.util.ArrayList;

public class User {

    private final String id;
    private String username;
    private String email;
    private String password;
    private ArrayList<String> ownedGamesIds;
    private ArrayList<String> ownedDLCsIds;
    private String vorname;
    private String nachname;

    public User(String id, String username) {
        this.id = id;
        this.ownedGamesIds = new ArrayList<>();
        this.ownedDLCsIds = new ArrayList<>();
    }

    public ArrayList<String> getOwnedGamesIds() {
        return ownedGamesIds;
    }

    public void setOwnedGamesIds(ArrayList<String> ownedGamesIds) {
        this.ownedGamesIds = ownedGamesIds;
    }

    public void addOwnedGames(String gameId) {
        this.ownedGamesIds.add(gameId);
    }

    public ArrayList<String> getOwnedDLCsIds() {
        return ownedDLCsIds;
    }

    public void setOwnedDLCsIds(ArrayList<String> ownedDLCsIds) {
        this.ownedDLCsIds = ownedDLCsIds;
    }

    public void addOwnedDLCs(String dlsId) {
        this.ownedDLCsIds.add(dlsId);
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }
}
