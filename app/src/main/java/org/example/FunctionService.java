import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class FunctionService {
    private final Scanner scanner;
    private User user;
    private ArrayList<User> users;
    private ArrayList<Games> games;
    private ArrayList<DLC> dlcs;
    private final ArrayList<String> choices;

    private static final String CSV_SEPARATOR = ";";
    private static final String LIST_SEPARATOR = "\\|";
    private static final Path DATA_DIR = Paths.get("data", "csv");
    private static final String USERS_HEADER = "username;email;password;ownedGamesIds;ownedDLCsIds";
    private static final String GAMES_HEADER = "id;titel;description;price;releaseDate";
    private static final String DLCS_HEADER = "id;dlcName;gameTitle;description;price;releaseDate";

    public FunctionService() {
        this.scanner = new Scanner(System.in);
        users = new ArrayList<User>();
        games = new ArrayList<Games>();
        dlcs = new ArrayList<DLC>();
        choices = new ArrayList<String>();

        //add choices for user interaction
        choices.add("exit/verlassen (e ), ");
        choices.add("logout/abmelden (l), ");
        choices.add("spiele (s), ");
        choices.add("dlcs (d), ");
        choices.add("benutzerverwaltung (u) ");
    }

    /**
     * Lädt die Daten und startet die Hauptschleife des Programms, in der der Benutzer verschiedene Aktionen ausführen kann.
     * @return 1, wenn das Laden der Daten fehlschlägt, 0, wenn das Programm erfolgreich gestartet wird.
     */
    public int loop() {
        System.out.println("loading data...");
        boolean loadedStatus = loadData();
        if (!loadedStatus) {
            System.out.println("loading failed");
            return 1;
        }
        System.out.println("loading successful");
        System.out.println("Willkommen im Game Store!"
                + "\nHier können Sie Spiele und DLCs kaufen, Ihre Spielzeit verwalten und vieles mehr."
                + "\nBitte melden Sie sich an, um fortzufahren."
        );
        while (true) {
            if (user == null) {
                initializeUser();
            }
            // Angabe der Auswahlmöglichkeiten für den Benutzer
            System.out.print("Bitte wählen Sie: ");
            choices.forEach(System.out::print);
            System.out.print("\nEingabe: ");

            String choice = this.scanner.next();
            switch (choice.toLowerCase()) {
                case "e":
                    if (!saveData()) {
                        System.out.println("Hinweis: Daten konnten vor dem Beenden nicht gespeichert werden.");
                    }
                    System.out.println("Vielen Dank für Ihren Besuch im Game Store. Auf Wiedersehen!");
                    return 0; // Beendet die Schleife und damit das Programm
                case "l":
                    if (!saveData()) {
                        System.out.println("Hinweis: Daten konnten beim Abmelden nicht gespeichert werden.");
                    }
                    System.out.println("Sie wurden erfolgreich abgemeldet.");
                    user = null; // Setzt den Benutzer auf null, um die Anmeldung zurückzusetzen
                    break;
                case "s":
                    initializeGames();
                    break;
                case "d":
                    initializeDLC();
                    break;
                case "u":
                    userAdministration();
                    break;
                default:
                    System.out.println("Ungültige Eingabe. Bitte versuchen Sie es erneut.");
            }
        }
    }

    private boolean loadData() {
        Path usersFile = DATA_DIR.resolve("users.csv");
        Path gamesFile = DATA_DIR.resolve("games.csv");
        Path dlcsFile = DATA_DIR.resolve("dlcs.csv");

        try {
            Files.createDirectories(DATA_DIR);
            ensureFileWithHeader(usersFile, USERS_HEADER);
            ensureFileWithHeader(gamesFile, GAMES_HEADER);
            ensureFileWithHeader(dlcsFile, DLCS_HEADER);

            users.clear();
            games.clear();
            dlcs.clear();

            loadUsersFromCsv(usersFile);
            loadGamesFromCsv(gamesFile);
            loadDlcsFromCsv(dlcsFile);

            System.out.println("Loaded users: " + users.size());
            System.out.println("Loaded games: " + games.size());
            System.out.println("Loaded DLCs: " + dlcs.size());
            return true;
        } catch (IOException ex) {
            System.out.println("Fehler beim Laden der CSV-Daten: " + ex.getMessage());
            return false;
        }
    }

    private boolean saveData() {
        Path usersFile = DATA_DIR.resolve("users.csv");
        Path gamesFile = DATA_DIR.resolve("games.csv");
        Path dlcsFile = DATA_DIR.resolve("dlcs.csv");

        try {
            Files.createDirectories(DATA_DIR);
            writeUsersToCsv(usersFile);
            writeGamesToCsv(gamesFile);
            writeDlcsToCsv(dlcsFile);
            return true;
        } catch (IOException ex) {
            System.out.println("Fehler beim Speichern der CSV-Daten: " + ex.getMessage());
            return false;
        }
    }

    private void writeUsersToCsv(Path usersFile) throws IOException {
        List<String> lines = new ArrayList<>();
        lines.add(USERS_HEADER);

        for (User currentUser : users) {
            String gamesList = String.join("|", currentUser.getOwnedGamesIds());
            String dlcsList = String.join("|", currentUser.getOwnedDLCsIds());
            lines.add(
                    sanitizeCsvValue(currentUser.getUsername()) + CSV_SEPARATOR
                            + sanitizeCsvValue(currentUser.getEmail()) + CSV_SEPARATOR
                            + sanitizeCsvValue(currentUser.getPassword()) + CSV_SEPARATOR
                            + sanitizeCsvValue(gamesList) + CSV_SEPARATOR
                            + sanitizeCsvValue(dlcsList)
            );
        }

        Files.write(usersFile, lines, StandardCharsets.UTF_8);
    }

    private void writeGamesToCsv(Path gamesFile) throws IOException {
        List<String> lines = new ArrayList<>();
        lines.add(GAMES_HEADER);

        for (Games game : games) {
            String releaseDate = game.getReleaseDate() == null ? "" : String.valueOf(game.getReleaseDate().getTime());
            lines.add(
                    sanitizeCsvValue(game.getId()) + CSV_SEPARATOR
                            + sanitizeCsvValue(game.getTitel()) + CSV_SEPARATOR
                            + sanitizeCsvValue(game.getDescription()) + CSV_SEPARATOR
                            + game.getPrice() + CSV_SEPARATOR
                            + releaseDate
            );
        }

        Files.write(gamesFile, lines, StandardCharsets.UTF_8);
    }

    private void writeDlcsToCsv(Path dlcsFile) throws IOException {
        List<String> lines = new ArrayList<>();
        lines.add(DLCS_HEADER);

        for (DLC dlc : dlcs) {
            String releaseDate = dlc.getReleaseDate() == null ? "" : String.valueOf(dlc.getReleaseDate().getTime());
            lines.add(
                    sanitizeCsvValue(dlc.getId()) + CSV_SEPARATOR
                            + sanitizeCsvValue(dlc.getDlcName()) + CSV_SEPARATOR
                            + sanitizeCsvValue(dlc.getGameTitle()) + CSV_SEPARATOR
                            + sanitizeCsvValue(dlc.getDescription()) + CSV_SEPARATOR
                            + dlc.getPrice() + CSV_SEPARATOR
                            + releaseDate
            );
        }

        Files.write(dlcsFile, lines, StandardCharsets.UTF_8);
    }

    private String sanitizeCsvValue(String value) {
        if (value == null) {
            return "";
        }
        return value
                .replace(CSV_SEPARATOR, ",")
                .replace("\r", " ")
                .replace("\n", " ");
    }

    private void ensureFileWithHeader(Path file, String header) throws IOException {
        if (Files.notExists(file)) {
            Files.write(file, Collections.singletonList(header), StandardCharsets.UTF_8);
            return;
        }

        if (Files.size(file) == 0L) {
            Files.write(file, Collections.singletonList(header), StandardCharsets.UTF_8);
        }
    }

    private void loadUsersFromCsv(Path usersFile) throws IOException {
        List<String> lines = Files.readAllLines(usersFile, StandardCharsets.UTF_8);
        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            if (line.isEmpty()) {
                continue;
            }

            String[] values = line.split(CSV_SEPARATOR, -1);
            if (values.length < 5) {
                System.out.println("users.csv Zeile " + (i + 1) + " uebersprungen: zu wenige Spalten.");
                continue;
            }

            String username = values[0].trim();
            if (username.isEmpty()) {
                System.out.println("users.csv Zeile " + (i + 1) + " uebersprungen: username fehlt.");
                continue;
            }

            User loadedUser = new User(username);
            loadedUser.setEmail(values[1].trim());
            loadedUser.setPassword(values[2].trim());
            loadedUser.setOwnedGamesIds(parseIdList(values[3]));
            loadedUser.setOwnedDLCsIds(parseIdList(values[4]));
            users.add(loadedUser);
        }
    }

    private void loadGamesFromCsv(Path gamesFile) throws IOException {
        List<String> lines = Files.readAllLines(gamesFile, StandardCharsets.UTF_8);
        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            if (line.isEmpty()) {
                continue;
            }

            String[] values = line.split(CSV_SEPARATOR, -1);
            if (values.length < 5) {
                System.out.println("games.csv Zeile " + (i + 1) + " uebersprungen: zu wenige Spalten.");
                continue;
            }

            String id = values[0].trim();
            if (id.isEmpty()) {
                System.out.println("games.csv Zeile " + (i + 1) + " uebersprungen: id fehlt.");
                continue;
            }

            Games loadedGame = new Games(id);
            loadedGame.setTitel(values[1].trim());
            loadedGame.setDescription(values[2].trim());

            Double price = parseDouble(values[3].trim());
            if (price == null) {
                System.out.println("games.csv Zeile " + (i + 1) + " uebersprungen: ungueltiger Preis.");
                continue;
            }
            loadedGame.setPrice(price);

            Date releaseDate = parseDate(values[4].trim());
            if (releaseDate != null) {
                loadedGame.setReleaseDate(releaseDate);
            }

            games.add(loadedGame);
        }
    }

    private void loadDlcsFromCsv(Path dlcsFile) throws IOException {
        List<String> lines = Files.readAllLines(dlcsFile, StandardCharsets.UTF_8);
        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            if (line.isEmpty()) {
                continue;
            }

            String[] values = line.split(CSV_SEPARATOR, -1);
            if (values.length < 6) {
                System.out.println("dlcs.csv Zeile " + (i + 1) + " uebersprungen: zu wenige Spalten.");
                continue;
            }

            String id = values[0].trim();
            if (id.isEmpty()) {
                System.out.println("dlcs.csv Zeile " + (i + 1) + " uebersprungen: id fehlt.");
                continue;
            }

            DLC loadedDlc = new DLC(id);
            loadedDlc.setDlcName(values[1].trim());
            loadedDlc.setGameTitle(values[2].trim());
            loadedDlc.setDescription(values[3].trim());

            Double price = parseDouble(values[4].trim());
            if (price == null) {
                System.out.println("dlcs.csv Zeile " + (i + 1) + " uebersprungen: ungueltiger Preis.");
                continue;
            }
            loadedDlc.setPrice(price);

            Date releaseDate = parseDate(values[5].trim());
            if (releaseDate != null) {
                loadedDlc.setReleaseDate(releaseDate);
            }

            dlcs.add(loadedDlc);
        }
    }

    private ArrayList<String> parseIdList(String rawValue) {
        ArrayList<String> ids = new ArrayList<>();
        String trimmed = rawValue == null ? "" : rawValue.trim();
        if (trimmed.isEmpty()) {
            return ids;
        }

        String[] splitValues = trimmed.split(LIST_SEPARATOR);
        for (String value : splitValues) {
            String id = value.trim();
            if (!id.isEmpty()) {
                ids.add(id);
            }
        }
        return ids;
    }

    private Double parseDouble(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        try {
            return Double.parseDouble(value.replace(',', '.'));
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    private Date parseDate(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }

        String normalized = value.trim();

        try {
            long timestamp = Long.parseLong(normalized);
            return new Date(timestamp);
        } catch (NumberFormatException ignored) {
            // not a timestamp
        }

        String[] patterns = {"yyyy-MM-dd", "dd.MM.yyyy"};
        for (String pattern : patterns) {
            SimpleDateFormat formatter = new SimpleDateFormat(pattern);
            formatter.setLenient(false);
            try {
                return formatter.parse(normalized);
            } catch (ParseException ignored) {
                // try next format
            }
        }

        return null;
    }

    private void userAdministration() {
        while (true) {
            System.out.println("Die Optionen sind:"
                    + "Spiel hinzufügen (h)"
                    + ", Spiel daten abrufen (a)"
                    + ", Spielzeit hinzufügen (t)"
                    + ", Benutzerinformationen anzeigen (i)"
                    + ", Benutzerinformationen aktualisieren (u)"
                    + ", Benutzer löschen (d)"
                    + ", Zurück zum Hauptmenü (e)"
            );
            String choice = this.scanner.next();
            switch (choice.toLowerCase()) {
                case "h": // Spiel hinzufügen
                    break;
                case "a": // Spiel daten abrufen
                    break;
                case "t": // Spielzeit hinzufügen
                    break;
                case "i": // Benutzerinformationen anzeigen
                    break;
                case "u": // Benutzerinformationen aktualisieren
                    break;
                case "d": // Benutzer löschen
                    break;
                case "e": // Zurück zum Hauptmenü
                    return;
                default:
                    System.out.println("Ungültige Eingabe. Bitte versuchen Sie es erneut.");
            }
        }
    }

    /**
     * Erstellt einen neuen Benutzer, indem Vorname und Nachname abgefragt werden.
     */
    private User createUser() {
        System.out.println("Bitte Ihren Benutzernamen eingeben");
        String vorname = this.scanner.next();

        return new User(vorname);
    }

    /**
     * Initialisiert die Login-Sitzung und ermöglicht dem Benutzer, sich entweder anzumelden oder zu registrieren.
     */
    private void initializeUser() {
        System.out.println("Willkommen im Game Store!"
                + "\nBitte melden Sie sich an, um fortzufahren."
                + "\nSie können sich anmelden oder registrieren (l für Login, r für Registrierung)");
        String choice = this.scanner.next();
        if (choice.equalsIgnoreCase("l")) {
            user = searchUser();
        } else if (choice.equalsIgnoreCase("r")) {
            user = createUser();
            users.add(user);
            if (!saveData()) {
                System.out.println("Hinweis: Neuer Benutzer konnte nicht gespeichert werden.");
            }
        } else {
            System.out.println("Ungültige Eingabe. Bitte versuchen Sie es erneut.");
            initializeUser();
        }
        if (user == null) {
            System.out.println("Ungültiger User. Bitte versuchen Sie es erneut.");
            initializeUser();
        }
    }

    public User searchUser() {
        // TODO: Implementieren Sie die Logik, um einen Benutzer zu suchen und sich anzumelden.
        System.out.println("Die Login-Funktionalität ist noch nicht implementiert. Bitte registrieren Sie sich zuerst.");
        return null;
    }

    public void initializeGames() {
        System.out.println("Willkommen im Game Store!"
                + "\nBitte wählen Sie: Game hinzufügen oder suchen (h für Hinzufügen, s für Suchen, l für auflisten)");
        String choice = this.scanner.next();
        if (choice.equalsIgnoreCase("s")) {
            // TODO: Implementieren Sie die Logik, um ein Spiel zu suchen und anzuzeigen.
            initializeGames();
        } else if (choice.equalsIgnoreCase("h")) {
            games.add(createGames());
            if (!saveData()) {
                System.out.println("Hinweis: Spiel konnte nicht gespeichert werden.");
            }
        } else if (choice.equalsIgnoreCase("l")) {
            if (games.isEmpty()) {
                System.out.println("Keine Spiele verfügbar.");
            } else {
                System.out.println("Verfügbare Spiele:");
                for (Games game : games) {
                    System.out.println("- " + game.getTitel() + " (ID: " + game.getId() + ")");
                }
            }
        } else {
            System.out.println("Ungültige Eingabe. Bitte versuchen Sie es erneut.");
            initializeGames();
        }
    }

    public Games createGames() {
        Games games = new Games(UUID.randomUUID().toString());

        System.out.println("Bitte geben Sie den Titel des Spiels ein");
        games.setTitel(this.scanner.next());

        System.out.println("Bitte geben Sie die Beschreibung des Spiels ein");
        games.setDescription(this.scanner.next());

        System.out.println("Bitte geben Sie den Preis des Spiels ein (z. B. 59,99)");
        games.setPrice(this.scanner.nextDouble());

        System.out.println("Bitte geben Sie das Erscheinungsjahr des Spiels ein (z. B. 2024)");
        int year = this.scanner.nextInt();
        System.out.println("Bitte geben Sie den Erscheinungsmonat des Spiels ein (1-12)");
        int month = this.scanner.nextInt();
        System.out.println("Bitte geben Sie den Erscheinungstag des Spiels ein (1-31)");
        int day = this.scanner.nextInt();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day, 0, 0, 0); // month - 1 ist korrekt, aber Warnung wegen Calendar-Konstanten
        calendar.set(Calendar.MONTH, month - 1); // explizit Calendar.MONTH setzen
        calendar.set(Calendar.MILLISECOND, 0);
        Date date = calendar.getTime();

        games.setReleaseDate(date);

        return games;
    }

    public void initializeDLC() {
        System.out.println("Willkommen im DLC-Store!"
                + "\nBitte wählen Sie: DLC hinzufügen oder suchen (h für Hinzufügen, s für Suchen, l für auflisten)");
        String choice = this.scanner.next();
        if (choice.equalsIgnoreCase("s")) {
            // TODO: Implementieren Sie die Logik, um einen DLC zu suchen und anzuzeigen.
            initializeDLC();
        } else if (choice.equalsIgnoreCase("h")) {
            dlcs.add(createDLC());
            if (!saveData()) {
                System.out.println("Hinweis: DLC konnte nicht gespeichert werden.");
            }
        } else if (choice.equalsIgnoreCase("l")) {
            if (dlcs.isEmpty()) {
                System.out.println("Keine DLCs verfügbar.");
            } else {
                System.out.println("Verfügbare DLCs:");
                for (DLC dlc : dlcs) {
                    System.out.println("- " + dlc.getDlcName() + " (ID: " + dlc.getId() + ")");
                }
            }
        } else {
            System.out.println("Ungültige Eingabe. Bitte versuchen Sie es erneut.");
            initializeDLC();
        }
    }

    public DLC createDLC() {
        DLC dlc = new DLC(UUID.randomUUID().toString());

        System.out.println("Bitte geben Sie den Namen des DLCs ein");
        dlc.setDlcName(this.scanner.next());

        System.out.println("Bitte geben Sie den Titel des zugehörigen Spiels ein");
        dlc.setGameTitle(this.scanner.next());

        System.out.println("Bitte geben Sie die Beschreibung des DLCs ein");
        dlc.setDescription(this.scanner.next());

        System.out.println("Bitte geben Sie den Preis des DLCs ein (z. B. 19.99)");
        dlc.setPrice(this.scanner.nextDouble());

        System.out.println("Bitte geben Sie das Erscheinungsjahr des DLCs ein (z. B. 2026)");
        int year = this.scanner.nextInt();
        System.out.println("Bitte geben Sie den Erscheinungsmonat des DLCs ein (1-12)");
        int month = this.scanner.nextInt();
        System.out.println("Bitte geben Sie den Erscheinungstag des DLCs ein (1-31)");
        int day = this.scanner.nextInt();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day, 0, 0, 0); // month - 1 ist korrekt, aber Warnung wegen Calendar-Konstanten
        calendar.set(Calendar.MONTH, month - 1); // explizit Calendar.MONTH setzen
        calendar.set(Calendar.MILLISECOND, 0);
        Date date = calendar.getTime();

        dlc.setReleaseDate(date);

        return dlc;
    }
}
