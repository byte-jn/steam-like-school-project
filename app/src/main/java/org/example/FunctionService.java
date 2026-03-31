package org.example;

import java.util.*;

public class FunctionService {
    private final Scanner scanner;
    private User user;
    private ArrayList<Games> games;
    private ArrayList<DLC> dlcs;

    public FunctionService() {
        this.scanner = new Scanner(System.in);
        games = new ArrayList<Games>();
        dlcs = new ArrayList<DLC>();
    }

    /**
     * Erstellt einen neuen Benutzer, indem Vorname und Nachname abgefragt werden.
     */
    public User createUser() {
        System.out.println("Bitte Ihren Benutzernamen eingeben");
        String vorname = this.scanner.next();

        return new User(vorname);
    }

    /**
     * Initialisiert die Login-Sitzung und ermöglicht dem Benutzer, sich entweder anzumelden oder zu registrieren.
     */
    public void initializeUser() {
        System.out.println("Willkommen im Game Store!"
                + "\nBitte melden Sie sich an, um fortzufahren."
                + "\nSie können sich anmelden oder registrieren (l für Login, r für Registrierung)");
        String choice = this.scanner.next();
        if (choice.equalsIgnoreCase("l")) {
            user = searchUser();
        } else if (choice.equalsIgnoreCase("r")) {
            user = createUser();
        } else {
            System.out.println("Ungültige Eingabe. Bitte versuchen Sie es erneut.");
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
                + "\nBitte wählen Sie: Game hinzufügen oder suchen (h für Hinzufügen, s für Suchen)");
        String choice = this.scanner.next();
        if (choice.equalsIgnoreCase("s")) {
            // TODO: Implementieren Sie die Logik, um ein Spiel zu suchen und anzuzeigen.
            initializeGames();
        } else if (choice.equalsIgnoreCase("h")) {
            games.add(createGames());
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

        System.out.println("Bitte geben Sie den Preis des Spiels ein (z. B. 59.99)");
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
                + "\nBitte wählen Sie: DLC hinzufügen oder suchen (h für Hinzufügen, s für Suchen)");
        String choice = this.scanner.next();
        if (choice.equalsIgnoreCase("s")) {
            // TODO: Implementieren Sie die Logik, um einen DLC zu suchen und anzuzeigen.
            initializeDLC();
        } else if (choice.equalsIgnoreCase("h")) {
            dlcs.add(createDLC());
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
