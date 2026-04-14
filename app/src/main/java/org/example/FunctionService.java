import java.util.*;

public class FunctionService {
    private final Scanner scanner;
    private User user;
    private ArrayList<Games> games;
    private ArrayList<DLC> dlcs;
    private final ArrayList<String> choices;

    public FunctionService() {
        this.scanner = new Scanner(System.in);
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
                    System.out.println("Vielen Dank für Ihren Besuch im Game Store. Auf Wiedersehen!");
                    return 0; // Beendet die Schleife und damit das Programm
                case "l":
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
        return true; // TODO: Implementieren Sie die Logik zum Laden der Daten aus einer Datei oder Datenbank.
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
