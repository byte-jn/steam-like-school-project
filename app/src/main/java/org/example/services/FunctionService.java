package org.example.services;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.example.dtos.DlcDto;
import org.example.dtos.GamesDto;
import org.example.dtos.UserDto;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.regex.Pattern;

@Singleton
public class FunctionService {

    private static final Pattern NAME_PATTERN =
            Pattern.compile("^[A-Za-z0-9 \\-!:'.,&]{1,255}$");
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[\\w._%+\\-]+@[\\w.\\-]+\\.[A-Za-z]{2,}$");

    public Scanner scanner;
    private final UserService userService;
    private final GamesService gamesService;
    private final DlcService dlcService;

    @Inject
    public FunctionService(UserService userService,
                           GamesService gamesService,
                           DlcService dlcService) {
        this.scanner = new Scanner(System.in);
        this.userService = userService;
        this.gamesService = gamesService;
        this.dlcService = dlcService;
    }

    /**
     * Initialisiert die Login-Sitzung und ermoeglicht dem Benutzer,
     * sich entweder anzumelden oder zu registrieren.
     */
    public UserDto initializeUser() {
        System.out.println("Willkommen im Game Store!"
                + "\nBitte melden Sie sich an, um fortzufahren."
                + "\n(l = Login, r = Registrierung)");
        String choice = this.scanner.next();
        if (choice.equalsIgnoreCase("l")) {
            UserDto found = searchUser();
            if (found != null) {
                return found;
            }
            return initializeUser();
        } else if (choice.equalsIgnoreCase("r")) {
            return createUser();
        } else {
            System.out.println("Ungueltige Eingabe. Bitte versuchen Sie es erneut.");
            return initializeUser();
        }
    }

    private UserDto searchUser() {
        System.out.println("Bitte geben Sie Ihren Benutzernamen ein");
        String username = this.scanner.next();
        Optional<UserDto> result = userService.findByUsername(username);
        if (result.isPresent()) {
            System.out.println("Willkommen zurueck, " + result.get().getFirstname() + "!");
            return result.get();
        }
        System.out.println("Benutzer nicht gefunden. Bitte registrieren Sie sich zuerst.");
        return null;
    }

    /**
     * Erstellt einen neuen Benutzer und speichert ihn ueber den UserService.
     */
    public UserDto createUser() {
        System.out.println("Bitte geben Sie einen Benutzernamen ein");
        String username = this.scanner.next();

        String email = readValidInput(
                "Bitte geben Sie Ihre E-Mail-Adresse ein",
                EMAIL_PATTERN,
                "Ungueltige E-Mail-Adresse. Bitte erneut versuchen.");

        System.out.println("Bitte geben Sie ein Passwort ein");
        String password = this.scanner.next();

        System.out.println("Bitte geben Sie Ihren Vornamen ein");
        String firstname = this.scanner.next();

        System.out.println("Bitte geben Sie Ihren Nachnamen ein");
        String lastname = this.scanner.next();

        UserDto dto = new UserDto();
        dto.setUsername(username);
        dto.setEmail(email);
        dto.setPassword(password);
        dto.setFirstname(firstname);
        dto.setLastname(lastname);

        UserDto saved = userService.save(dto);
        System.out.println("Benutzer erfolgreich registriert! Willkommen, " + firstname + "!");
        return saved;
    }

    /**
     * Zeigt das Hauptmenue nach dem Login und verarbeitet die Auswahl des Benutzers.
     */
    public void mainMenu(UserDto user) {
        boolean running = true;
        while (running) {
            System.out.println("\n=== Hauptmenue ==="
                    + "\n[1] Alle Spiele anzeigen"
                    + "\n[2] Spiel zum Store hinzufuegen"
                    + "\n[3] Spiel kaufen (in Bibliothek)"
                    + "\n[4] Alle DLCs anzeigen"
                    + "\n[5] DLC zum Store hinzufuegen"
                    + "\n[6] DLC kaufen (in Bibliothek)"
                    + "\n[7] Benutzerverwaltung"
                    + "\n[8] Beenden");
            String choice = this.scanner.next();
            switch (choice) {
                case "1": listGames(); break;
                case "2": createGame(); break;
                case "3": user = addGameToLibrary(user); break;
                case "4": listDlcs(); break;
                case "5": createDlc(); break;
                case "6": user = addDlcToLibrary(user); break;
                case "7": user = userAdministration(user); break;
                case "8":
                    System.out.println("Auf Wiedersehen, " + user.getFirstname() + "!");
                    running = false;
                    break;
                default:
                    System.out.println("Ungueltige Eingabe. Bitte waehlen Sie 1-8.");
            }
        }
    }

    private void listGames() {
        List<GamesDto> games = gamesService.findAll();
        if (games.isEmpty()) {
            System.out.println("Keine Spiele im Store vorhanden.");
            return;
        }
        System.out.println("\n--- Alle Spiele ---");
        for (GamesDto game : games) {
            System.out.println("  [" + game.getId() + "] " + game.getTitel() + " - " + game.getPrice() + " EUR");
        }
    }

    private void listDlcs() {
        List<DlcDto> dlcs = dlcService.findAll();
        if (dlcs.isEmpty()) {
            System.out.println("Keine DLCs im Store vorhanden.");
            return;
        }
        System.out.println("\n--- Alle DLCs ---");
        for (DlcDto dlc : dlcs) {
            System.out.println("  [" + dlc.getId() + "] " + dlc.getDlcName()
                    + " (" + dlc.getGameTitle() + ") - " + dlc.getPrice() + " EUR");
        }
    }

    /**
     * Erstellt ein neues Spiel und speichert es ueber den GamesService.
     */
    public GamesDto createGame() {
        GamesDto dto = new GamesDto();

        dto.setTitel(readValidInput(
                "Bitte geben Sie den Titel des Spiels ein",
                NAME_PATTERN,
                "Ungültiger Titel. Erlaubt: Buchstaben, Zahlen, Leerzeichen, grundlegende Satzzeichen (max. 255)."));

        System.out.println("Bitte geben Sie die Beschreibung des Spiels ein");
        dto.setDescription(this.scanner.next());

        dto.setPrice(readNonNegativePrice("Bitte geben Sie den Preis des Spiels ein (z. B. 59.99)"));
        dto.setReleaseDate(readDate());

        GamesDto saved = gamesService.save(dto);
        System.out.println("Spiel '" + saved.getTitel() + "' erfolgreich gespeichert!");
        return saved;
    }

    /**
     * Erstellt einen neuen DLC und speichert ihn ueber den DlcService.
     */
    public DlcDto createDlc() {
        DlcDto dto = new DlcDto();

        dto.setDlcName(readValidInput(
                "Bitte geben Sie den Namen des DLCs ein",
                NAME_PATTERN,
                "Ungültiger Name. Erlaubt: Buchstaben, Zahlen, Leerzeichen, grundlegende Satzzeichen (max. 255)."));

        dto.setGameTitle(readValidInput(
                "Bitte geben Sie den Titel des zugehoerigen Spiels ein",
                NAME_PATTERN,
                "Ungültiger Spieltitel. Erlaubt: Buchstaben, Zahlen, Leerzeichen, grundlegende Satzzeichen (max. 255)."));

        System.out.println("Bitte geben Sie die Beschreibung des DLCs ein");
        dto.setDescription(this.scanner.next());

        dto.setPrice(readNonNegativePrice("Bitte geben Sie den Preis des DLCs ein (z. B. 19.99)"));
        dto.setReleaseDate(readDate());

        DlcDto saved = dlcService.save(dto);
        System.out.println("DLC '" + saved.getDlcName() + "' erfolgreich gespeichert!");
        return saved;
    }

    private UserDto addGameToLibrary(UserDto user) {
        List<GamesDto> games = gamesService.findAll();
        if (games.isEmpty()) {
            System.out.println("Keine Spiele im Store vorhanden.");
            return user;
        }
        listGames();
        System.out.println("Bitte geben Sie die ID des Spiels ein:");
        String gameId = this.scanner.next();

        boolean exists = games.stream().anyMatch(g -> g.getId().equals(gameId));
        if (!exists) {
            System.out.println("Spiel mit dieser ID nicht gefunden.");
            return user;
        }
        if (user.getOwnedGamesIds().contains(gameId)) {
            System.out.println("Dieses Spiel ist bereits in Ihrer Bibliothek.");
            return user;
        }
        user.addOwnedGameId(gameId);
        userService.update(user);
        System.out.println("Spiel erfolgreich zur Bibliothek hinzugefuegt.");
        return user;
    }

    private UserDto addDlcToLibrary(UserDto user) {
        List<DlcDto> dlcs = dlcService.findAll();
        if (dlcs.isEmpty()) {
            System.out.println("Keine DLCs im Store vorhanden.");
            return user;
        }
        listDlcs();
        System.out.println("Bitte geben Sie die ID des DLCs ein:");
        String dlcId = this.scanner.next();

        Optional<DlcDto> selected = dlcs.stream().filter(d -> d.getId().equals(dlcId)).findFirst();
        if (!selected.isPresent()) {
            System.out.println("DLC mit dieser ID nicht gefunden.");
            return user;
        }
        if (user.getOwnedDlcIds().contains(dlcId)) {
            System.out.println("Dieses DLC ist bereits in Ihrer Bibliothek.");
            return user;
        }
        DlcDto dlc = selected.get();
        boolean ownsBaseGame = gamesService.findAll().stream()
                .filter(g -> dlc.getGameTitle() != null && dlc.getGameTitle().equalsIgnoreCase(g.getTitel()))
                .anyMatch(g -> user.getOwnedGamesIds().contains(g.getId()));
        if (!ownsBaseGame) {
            System.out.println("Sie benoetigen das Basisspiel '" + dlc.getGameTitle() + "' um dieses DLC zu kaufen.");
            return user;
        }
        user.addOwnedDlcId(dlcId);
        userService.update(user);
        System.out.println("DLC erfolgreich zur Bibliothek hinzugefuegt.");
        return user;
    }

    private UserDto userAdministration(UserDto user) {
        boolean running = true;
        while (running) {
            System.out.println("\n=== Benutzerverwaltung ==="
                    + "\n[i] Benutzerinformationen anzeigen"
                    + "\n[u] E-Mail oder Passwort aktualisieren"
                    + "\n[d] Konto loeschen"
                    + "\n[e] Zurueck");
            String choice = this.scanner.next();
            switch (choice.toLowerCase()) {
                case "i": showUserInfo(user); break;
                case "u": user = updateUserInfo(user); break;
                case "d": deleteAccount(user); running = false; break;
                case "e": running = false; break;
                default: System.out.println("Ungueltige Eingabe.");
            }
        }
        return user;
    }

    private void showUserInfo(UserDto user) {
        System.out.println("\n--- Benutzerinformationen ---"
                + "\nBenutzername : " + user.getUsername()
                + "\nE-Mail       : " + user.getEmail()
                + "\nVorname      : " + user.getFirstname()
                + "\nNachname     : " + user.getLastname()
                + "\nGekaufte Spiele: " + user.getOwnedGamesIds().size()
                + "\nGekaufte DLCs  : " + user.getOwnedDlcIds().size());
    }

    private UserDto updateUserInfo(UserDto user) {
        String email = readValidInput(
                "Neue E-Mail-Adresse eingeben:",
                EMAIL_PATTERN,
                "Ungueltige E-Mail-Adresse. Bitte erneut versuchen.");
        System.out.println("Neues Passwort eingeben:");
        String password = this.scanner.next();

        user.setEmail(email);
        user.setPassword(password);
        userService.update(user);
        System.out.println("Benutzerinformationen erfolgreich aktualisiert.");
        return user;
    }

    private void deleteAccount(UserDto user) {
        System.out.println("Konto wirklich loeschen? (j/n)");
        String confirm = this.scanner.next();
        if (confirm.equalsIgnoreCase("j")) {
            userService.delete(user.getId());
            System.out.println("Konto erfolgreich geloescht. Auf Wiedersehen!");
        } else {
            System.out.println("Loeschvorgang abgebrochen.");
        }
    }

    private String readValidInput(String prompt, Pattern pattern, String errorMsg) {
        while (true) {
            System.out.println(prompt);
            String input = this.scanner.next();
            if (pattern.matcher(input).matches()) {
                return input;
            }
            System.out.println(errorMsg);
        }
    }

    private double readNonNegativePrice(String prompt) {
        while (true) {
            System.out.println(prompt);
            if (this.scanner.hasNextDouble()) {
                double value = this.scanner.nextDouble();
                if (value >= 0) {
                    return value;
                }
            } else {
                this.scanner.next();
            }
            System.out.println("Ungueltige Eingabe. Bitte geben Sie eine nicht-negative Zahl ein.");
        }
    }

    private Date readDate() {
        System.out.println("Erscheinungsjahr eingeben (z. B. 2024)");
        int year = this.scanner.nextInt();
        System.out.println("Erscheinungsmonat eingeben (1-12)");
        int month = this.scanner.nextInt();
        System.out.println("Erscheinungstag eingeben (1-31)");
        int day = this.scanner.nextInt();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
}
