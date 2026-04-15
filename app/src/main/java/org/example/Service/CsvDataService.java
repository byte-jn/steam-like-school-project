package Service;

import Entites.DLC;
import Entites.Games;
import Entites.User;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Übernimmt das Laden und Speichern aller Anwendungsdaten in CSV-Dateien.
 *
 * @author Jannis Lauer (jannis280@outlook.de)
 */
public class CsvDataService {
    private static final String CSV_SEPARATOR = ";";
    private static final String LIST_SEPARATOR = "\\|";
    private static final Path DATA_DIR = Paths.get("data", "csv");
    private static final String USERS_HEADER = "username;email;password;ownedGamesIds;ownedDLCsIds";
    private static final String GAMES_HEADER = "id;titel;description;price;releaseDate";
    private static final String DLCS_HEADER = "id;dlcName;gameTitle;description;price;releaseDate";

    /**
     * Lädt alle Benutzer-, Spiele- und DLC-Daten aus CSV-Dateien.
     *
     * @param users Ziel-Liste für Benutzer
     * @param games Ziel-Liste für Spiele
     * @param dlcs Ziel-Liste für DLCs
     * @return {@code true}, wenn das Laden erfolgreich war
     */
    public boolean loadAll(ArrayList<User> users, ArrayList<Games> games, ArrayList<DLC> dlcs) {
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

            loadUsersFromCsv(usersFile, users);
            loadGamesFromCsv(gamesFile, games);
            loadDlcsFromCsv(dlcsFile, dlcs);

            System.out.println("Loaded users: " + users.size());
            System.out.println("Loaded games: " + games.size());
            System.out.println("Loaded DLCs: " + dlcs.size());
            return true;
        } catch (IOException ex) {
            System.out.println("Fehler beim Laden der CSV-Daten: " + ex.getMessage());
            return false;
        }
    }

    /**
     * Speichert alle Benutzer-, Spiele- und DLC-Daten in CSV-Dateien.
     *
     * @param users Quell-Liste der Benutzer
     * @param games Quell-Liste der Spiele
     * @param dlcs Quell-Liste der DLCs
     * @return {@code true}, wenn das Speichern erfolgreich war
     */
    public boolean saveAll(ArrayList<User> users, ArrayList<Games> games, ArrayList<DLC> dlcs) {
        Path usersFile = DATA_DIR.resolve("users.csv");
        Path gamesFile = DATA_DIR.resolve("games.csv");
        Path dlcsFile = DATA_DIR.resolve("dlcs.csv");

        try {
            Files.createDirectories(DATA_DIR);
            writeUsersToCsv(usersFile, users);
            writeGamesToCsv(gamesFile, games);
            writeDlcsToCsv(dlcsFile, dlcs);
            return true;
        } catch (IOException ex) {
            System.out.println("Fehler beim Speichern der CSV-Daten: " + ex.getMessage());
            return false;
        }
    }

    /**
     * Stellt sicher, dass eine CSV-Datei existiert und mindestens den Header enthält.
     *
     * @param file Ziel-Datei
     * @param header erwartete Header-Zeile
     * @throws IOException bei Dateifehlern
     */
    private void ensureFileWithHeader(Path file, String header) throws IOException {
        if (Files.notExists(file)) {
            Files.write(file, Collections.singletonList(header), StandardCharsets.UTF_8);
            return;
        }

        if (Files.size(file) == 0L) {
            Files.write(file, Collections.singletonList(header), StandardCharsets.UTF_8);
        }
    }

    /**
     * Schreibt alle Benutzer in die Benutzer-CSV.
     *
     * @param usersFile Ziel-Datei
     * @param users Benutzerliste
     * @throws IOException bei Dateifehlern
     */
    private void writeUsersToCsv(Path usersFile, ArrayList<User> users) throws IOException {
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

    /**
     * Schreibt alle Spiele in die Spiele-CSV.
     *
     * @param gamesFile Ziel-Datei
     * @param games Spieleliste
     * @throws IOException bei Dateifehlern
     */
    private void writeGamesToCsv(Path gamesFile, ArrayList<Games> games) throws IOException {
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

    /**
     * Schreibt alle DLCs in die DLC-CSV.
     *
     * @param dlcsFile Ziel-Datei
     * @param dlcs DLC-Liste
     * @throws IOException bei Dateifehlern
     */
    private void writeDlcsToCsv(Path dlcsFile, ArrayList<DLC> dlcs) throws IOException {
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

    /**
     * Lädt Benutzer aus der Benutzer-CSV in die Ziel-Liste.
     *
     * @param usersFile Quell-Datei
     * @param users Ziel-Liste
     * @throws IOException bei Dateifehlern
     */
    private void loadUsersFromCsv(Path usersFile, ArrayList<User> users) throws IOException {
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

    /**
     * Lädt Spiele aus der Spiele-CSV in die Ziel-Liste.
     *
     * @param gamesFile Quell-Datei
     * @param games Ziel-Liste
     * @throws IOException bei Dateifehlern
     */
    private void loadGamesFromCsv(Path gamesFile, ArrayList<Games> games) throws IOException {
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

    /**
     * Lädt DLCs aus der DLC-CSV in die Ziel-Liste.
     *
     * @param dlcsFile Quell-Datei
     * @param dlcs Ziel-Liste
     * @throws IOException bei Dateifehlern
     */
    private void loadDlcsFromCsv(Path dlcsFile, ArrayList<DLC> dlcs) throws IOException {
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

    /**
     * Bereinigt Werte für die CSV-Ausgabe.
     *
     * @param value Rohwert
     * @return bereinigter CSV-Wert
     */
    private String sanitizeCsvValue(String value) {
        if (value == null) {
            return "";
        }
        return value
                .replace(CSV_SEPARATOR, ",")
                .replace("\r", " ")
                .replace("\n", " ");
    }

    /**
     * Zerlegt eine durch | getrennte ID-Liste.
     *
     * @param rawValue Rohwert aus CSV
     * @return Liste einzelner IDs
     */
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

    /**
     * Parst einen Zahlenwert für Preise.
     *
     * @param value Rohwert
     * @return geparster Wert oder {@code null}
     */
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

    /**
     * Parst ein Datum aus Timestamp oder Textformat.
     *
     * @param value Rohwert
     * @return Datum oder {@code null}
     */
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
}

