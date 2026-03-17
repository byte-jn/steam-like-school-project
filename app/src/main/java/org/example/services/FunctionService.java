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

@Singleton
public class FunctionService {

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

        System.out.println("Bitte geben Sie Ihre E-Mail-Adresse ein");
        String email = this.scanner.next();

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
                    + "\n[2] Spiel hinzufuegen"
                    + "\n[3] DLC hinzufuegen"
                    + "\n[4] Beenden");
            String choice = this.scanner.next();
            if (choice.equals("1")) {
                listGames();
            } else if (choice.equals("2")) {
                createGame();
            } else if (choice.equals("3")) {
                createDlc();
            } else if (choice.equals("4")) {
                System.out.println("Auf Wiedersehen, " + user.getFirstname() + "!");
                running = false;
            } else {
                System.out.println("Ungueltige Eingabe. Bitte waehlen Sie 1, 2, 3 oder 4.");
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
            System.out.println("  " + game.getTitel() + " - " + game.getPrice() + " EUR");
        }
    }

    /**
     * Erstellt ein neues Spiel und speichert es ueber den GamesService.
     */
    public GamesDto createGame() {
        GamesDto dto = new GamesDto();

        System.out.println("Bitte geben Sie den Titel des Spiels ein");
        dto.setTitel(this.scanner.next());

        System.out.println("Bitte geben Sie die Beschreibung des Spiels ein");
        dto.setDescription(this.scanner.next());

        System.out.println("Bitte geben Sie den Preis des Spiels ein (z. B. 59.99)");
        dto.setPrice(this.scanner.nextDouble());

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

        System.out.println("Bitte geben Sie den Namen des DLCs ein");
        dto.setDlcName(this.scanner.next());

        System.out.println("Bitte geben Sie den Titel des zugehoerigen Spiels ein");
        dto.setGameTitle(this.scanner.next());

        System.out.println("Bitte geben Sie die Beschreibung des DLCs ein");
        dto.setDescription(this.scanner.next());

        System.out.println("Bitte geben Sie den Preis des DLCs ein (z. B. 19.99)");
        dto.setPrice(this.scanner.nextDouble());

        dto.setReleaseDate(readDate());

        DlcDto saved = dlcService.save(dto);
        System.out.println("DLC '" + saved.getDlcName() + "' erfolgreich gespeichert!");
        return saved;
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
