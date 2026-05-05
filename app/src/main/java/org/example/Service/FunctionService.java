package Service;

import Entites.DLC;
import Entites.Game;
import Entites.User;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.util.UUID;

/**
 * Steuert den gesamten Konsolenablauf der Anwendung inklusive Menüs, Login und Benutzeraktionen.
 *
 * @author Jannis Lauer (jannis280@outlook.de)
 */
public class FunctionService {
    private final Scanner scanner;
    private final CsvDataService csvDataService;
    private User activeUser;
    private final ArrayList<User> users;
    private final ArrayList<Game> games;
    private final ArrayList<DLC> dlcs;
    private final ArrayList<String> choices;

    /**
     * Initialisiert Eingabe, Datenservice und In-Memory-Listen.
     */
    public FunctionService() {
        this.scanner = new Scanner(System.in);
        this.csvDataService = new CsvDataService();
        users = new ArrayList<>();
        games = new ArrayList<>();
        dlcs = new ArrayList<>();
        choices = new ArrayList<>();

        // Hinzufügen der Möglichkeiten
        choices.add("exit/verlassen (e), ");
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
            if (activeUser == null) {
                int exitcode = initializeUser();
                if (exitcode >= 0) {
                    return exitcode;
                }
            }
            // Angabe der Auswahlmöglichkeiten für den Benutzer
            System.out.print("Bitte wählen Sie: ");
            choices.forEach(System.out::print);
            System.out.print("\nEingabe: ");

            String choice = this.scanner.nextLine();
            switch (choice.toLowerCase()) {
                case "e":
                    if (!saveData()) {
                        System.out.println("Hinweis: Daten konnten vor dem Beenden nicht gespeichert werden.");
                        return 1; // Beendet mit Fehlercode, wenn Speichern fehlschlägt
                    }
                    System.out.println("Vielen Dank für Ihren Besuch im Game Store. Auf Wiedersehen!");
                    return 0; // Beendet die Schleife und damit das Programm
                case "l":
                    if (!saveData()) {
                        System.out.println("Hinweis: Daten konnten beim Abmelden nicht gespeichert werden.");
                    }
                    System.out.println("Sie wurden erfolgreich abgemeldet.");
                    activeUser = null; // Setzt den Benutzer auf null, um die Anmeldung zurückzusetzen
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
                    System.out.println("Ungültige Eingabe. Bitte versuchen Sie es erneut." + choice);
            }
        }
    }

    /**
     * Lädt Benutzer-, Spiele- und DLC-Daten aus dem CSV-Service.
     *
     * @return {@code true}, wenn das Laden erfolgreich war
     */
    private boolean loadData() {
        return csvDataService.loadAll(users, games, dlcs);
    }

    /**
     * Speichert Benutzer-, Spiele- und DLC-Daten in CSV-Dateien.
     *
     * @return {@code true}, wenn das Speichern erfolgreich war
     */
    private boolean saveData() {
        return csvDataService.saveAll(users, games, dlcs);
    }

    /**
     * Zeigt das Benutzerverwaltungsmenü an.
     */
    private void userAdministration() {
        while (true) {
            System.out.println("Die Optionen sind:"
                    + ", Benutzerinformationen anzeigen (i)"
                    + ", Benutzerinformationen aktualisieren (u)"
                    + ", Benutzer löschen (d)"
                    + ", Zurück zum Hauptmenü (e)"
            );
            String choice = this.scanner.nextLine();
            switch (choice.toLowerCase()) {
                case "i": // Benutzerinformationen anzeigen
                    System.out.println("Benutzerinformationen für '" + activeUser.getUsername() + "':");
                    System.out.println("- Benutzername: " + activeUser.getUsername());
                    System.out.println("- Besessene Spiele: ");
                    for (String gameId: activeUser.getOwnedGamesIds()) {
                        Game ownedGames = LookupService.findGameById(games, gameId);
                        System.out.println(
                            "   Titel: " + ownedGames.getTitel() +
                            ", Preis: " + ownedGames.getPrice() +
                            ", Erscheinungsdatum: " + ownedGames.getReleaseDate().toString() +
                            ", Beschreibung: " + ownedGames.getDescription()
                        );
                    }
                    System.out.println("- Besessene DLCs: " + activeUser.getOwnedDLCsIds().toString());
                    for (String dlcId: activeUser.getOwnedDLCsIds()) {
                        DLC owendDlc = LookupService.findDlcById(dlcs, dlcId);
                        System.out.println(
                            "   Name: " + owendDlc.getDlcName() +
                            ", Spiel: " + owendDlc.getGameTitle() +
                            ", Preis: " + owendDlc.getPrice() +
                            ", Erscheinungsdatum: " + owendDlc.getReleaseDate()
                        );
                    }
                    break;
                case "u": // Benutzerinformationen aktualisieren
                    System.out.println("Bitte neuen Benutzernamen eingeben:");
                    String newUsername = this.scanner.nextLine();
                    if (newUsername.trim().isEmpty()) {
                        System.out.println("Ungültiger Benutzername. Nicht aktualisiert.");
                        break;
                    }
                    System.out.println("- Benutzername: " + newUsername);
                    break;
                case "d": // Benutzer löschen
                    System.out.println("Bist du dir sicher? tippe 'remove'");
                    if (!this.scanner.nextLine().equalsIgnoreCase("remove")) {
                        System.out.println("Benutzerlöschung abgebrochen.");
                        break;
                    }
                    System.out.println("Benutzer '" + activeUser.getUsername() + "' wird gelöscht...");
                    users.remove(activeUser);
                    if (!saveData()) {
                        System.out.println("Hinweis: Benutzer wurde gelöscht, konnte aber nicht gespeichert werden.");
                    }
                    activeUser = null;
                    return;
                case "e": // Zurück zum Hauptmenü
                    return;
                default:
                    System.out.println("Ungültige Eingabe. Bitte versuchen Sie es erneut." + choice);
            }
        }
    }

    /**
     * Erstellt einen neuen Benutzer anhand der Eingabe des Benutzernamens.
     *
     * @return neu erstellter Benutzer
     */
    private User createUser() {
        System.out.println("Bitte Ihren Benutzernamen eingeben");
        String vorname = this.scanner.nextLine();

        return new User(vorname);
    }

    /**
     * Initialisiert die Login-Sitzung und ermöglicht dem Benutzer, sich entweder anzumelden oder zu registrieren.
     */
    private int initializeUser() {
        while (activeUser == null) {
            System.out.println("Willkommen im Game Store!"
                    + "\nBitte melden Sie sich an, um fortzufahren."
                    + "\nSie können sich anmelden oder registrieren (l für Login, r für Registrierung, e für verlassen)");

            String choice = this.scanner.nextLine();
            if (choice.equalsIgnoreCase("l")) {
                activeUser = loginUser();
            } else if (choice.equalsIgnoreCase("r")) {
                activeUser = createUser();
                users.add(activeUser);
                if (!saveData()) {
                    System.out.println("Hinweis: Neuer Benutzer konnte nicht gespeichert werden.");
                }
            } else if  (choice.equalsIgnoreCase("e")) {
                System.out.println("Vielen Dank für Ihren Besuch im Game Store. Auf Wiedersehen!");
                return 0;
            } else {
                System.out.println("Ungültige Eingabe. Bitte versuchen Sie es erneut." + choice);
            }

            if (activeUser == null) {
                System.out.println("Ungültiger User. Bitte versuchen Sie es erneut.");
            }
        }
        return -1;
    }

    /**
     * Führt den Login über den Benutzernamen durch.
     *
     * @return eingeloggter Benutzer oder {@code null}
     */
    public User loginUser() {
        if (users.isEmpty()) {
            System.out.println("Es sind noch keine Benutzer registriert. Bitte registrieren Sie sich zuerst.");
            return null;
        }

        System.out.println("Bitte Benutzernamen für den Login eingeben:");
        String username = this.scanner.nextLine();

        User foundUser = LookupService.findUserByUsername(users, username);
        if (foundUser == null) {
            System.out.println("Benutzer nicht gefunden.");
            return null;
        }

        System.out.println("Login erfolgreich. Willkommen, " + foundUser.getUsername() + "!");
        return foundUser;
    }

    /**
     * Zeigt das Spielmenü an und ermöglicht Erstellen, Hinzufügen und Auflisten.
     */
    public void initializeGames() {
        while (true) {
            System.out.println("Willkommen im Game Store!"
                    + "\nBitte wählen Sie: Game hinzufügen oder suchen (c für Erstellen, a für hinzufügen, r für entfernen, l für auflisten, e für exit)");
            String choice = this.scanner.nextLine();
            if (choice.equalsIgnoreCase("a")) {
                addGames();
            } else if (choice.equalsIgnoreCase("c")) {
                games.add(createGames());
                if (!saveData()) {
                    System.out.println("Hinweis: Spiel konnte nicht gespeichert werden.");
                }
            } else if (choice.equalsIgnoreCase("r")) {
                if (games.isEmpty()) {
                    System.out.println("Keine Spiele verfügbar, die entfernt werden können.");
                    return;
                }

                System.out.println("Verfügbare Spiele:");
                for (Game game : games) {
                    System.out.println("- " + game.getTitel() + " (ID: " + game.getId() + ")");
                }

                System.out.println("Bitte geben Sie die Namen des Spiels ein, das Sie entfernen möchten:");
                String gameName = this.scanner.nextLine();

                Game selectedGame = LookupService.findGameByName(games, gameName);
                if (selectedGame == null) {
                    System.out.println("Spiel mit dieser Namen wurde nicht gefunden.");
                    return;
                }

                games.remove(selectedGame);
                if (!saveData()) {
                    System.out.println("Hinweis: Spiel wurde entfernt, konnte aber nicht gespeichert werden.");
                    return;
                }

                for (User u : users) {
                    u.removeGame(selectedGame.getId());
                }

                System.out.println("Spiel erfolgreich entfernt: " + selectedGame.getTitel());
            } else if (choice.equalsIgnoreCase("l")) {
                if (games.isEmpty()) {
                    System.out.println("Keine Spiele verfügbar.");
                } else {
                    System.out.println("Verfügbare Spiele:");
                    for (Game game : games) {
                        System.out.println("- " + game.getTitel() + " (ID: " + game.getId() + ")");
                    }
                }
            } else if (choice.equalsIgnoreCase("e")) {
                return; // Zurück zum Hauptmenü
            } else {
                System.out.println("Ungültige Eingabe. Bitte versuchen Sie es erneut.");
            }
        }
    }

    /**
     * Fügt ein vorhandenes Spiel per ID zur Bibliothek des aktiven Benutzers hinzu.
     */
    private void addGames() {
        if (activeUser == null) {
            System.out.println("Sie sind nicht eingeloggt.");
            return;
        }

        if (games.isEmpty()) {
            System.out.println("Keine Spiele verfügbar, die hinzugefügt werden können.");
            return;
        }

        System.out.println("Verfügbare Spiele:");
        for (Game game : games) {
            System.out.println("- " + game.getTitel() + " (ID: " + game.getId() + ")");
        }

        System.out.println("Bitte geben Sie die ID des Spiels ein, das Sie hinzufügen möchten:");
        String gameName = this.scanner.nextLine();

        Game selectedGame = LookupService.findGameByName(games, gameName);
        if (selectedGame == null) {
            System.out.println("Spiel mit dieser ID wurde nicht gefunden.");
            return;
        }

        if (activeUser.getOwnedGamesIds().contains(selectedGame.getId())) {
            System.out.println("Dieses Spiel ist bereits in Ihrer Bibliothek.");
            return;
        }

        activeUser.addOwnedGames(selectedGame.getId());
        if (!saveData()) {
            System.out.println("Hinweis: Spiel wurde hinzugefügt, konnte aber nicht gespeichert werden.");
            return;
        }

        System.out.println("Spiel erfolgreich hinzugefügt: " + selectedGame.getTitel());
    }

    /**
     * Erstellt ein neues Spiel über Konsoleneingaben.
     *
     * @return neu erstelltes Spiel
     */
    public Game createGames() {
        Game games = new Game(UUID.randomUUID().toString());

        System.out.println("Bitte geben Sie den Titel des Spiels ein");
        games.setTitel(this.scanner.nextLine());

        System.out.println("Bitte geben Sie die Beschreibung des Spiels ein");
        games.setDescription(this.scanner.nextLine());

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

    /**
     * Zeigt das DLC-Menü an und ermöglicht Erstellen, Hinzufügen und Auflisten.
     */
    public void initializeDLC() {
        while (true) {
            System.out.println("Willkommen im DLC-Store!"
                    + "\nBitte wählen Sie: DLC hinzufügen oder suchen (c für erstellen, a für hinzufügen, r für entfernen, l für auflisten, e für verlassen)");
            String choice = this.scanner.nextLine();
            if (choice.equalsIgnoreCase("a")) {
                addDLC();
            } else if (choice.equalsIgnoreCase("c")) {
                dlcs.add(createDLC());
                if (!saveData()) {
                    System.out.println("Hinweis: DLC konnte nicht gespeichert werden.");
                }
            }
            else if (choice.equalsIgnoreCase("r")) {
                if (dlcs.isEmpty()) {
                    System.out.println("Keine DLCs verfügbar, die entfernt werden können.");
                    return;
                }

                System.out.println("Verfügbare DLCs:");
                for (DLC dlc : dlcs) {
                    System.out.println("- " + dlc.getDlcName() + " (ID: " + dlc.getId() + ", Spiel: " + dlc.getGameTitle() + ")");
                }

                System.out.println("Bitte geben Sie die Namen des DLCs ein, das Sie entfernen möchten:");
                String dlcName = this.scanner.nextLine();

                DLC selectedDlc = LookupService.findDlcByName(dlcs, dlcName);
                if (selectedDlc == null) {
                    System.out.println("DLC mit dieser Namen wurde nicht gefunden.");
                    return;
                }

                dlcs.remove(selectedDlc);
                if (!saveData()) {
                    System.out.println("Hinweis: DLC wurde entfernt, konnte aber nicht gespeichert werden.");
                    return;
                }

                for (User u : users) {
                    u.removeDlc(selectedDlc.getId());
                }

                System.out.println("DLC erfolgreich entfernt: " + selectedDlc.getDlcName());
            } else if (choice.equalsIgnoreCase("l")) {
                if (dlcs.isEmpty()) {
                    System.out.println("Keine DLCs verfügbar.");
                } else {
                    System.out.println("Verfügbare DLCs:");
                    for (DLC dlc : dlcs) {
                        System.out.println("- " + dlc.getDlcName() + " (ID: " + dlc.getId() + ")");
                    }
                }
            } else if (choice.equalsIgnoreCase("e")) {
                return; // Zurück zum Hauptmenü
            } else {
                System.out.println("Ungültige Eingabe. Bitte versuchen Sie es erneut." + choice);
            }
        }
    }

    /**
     * Fügt ein vorhandenes DLC per ID zur Bibliothek des aktiven Benutzers hinzu.
     */
    private void addDLC() {
        if (activeUser == null) {
            System.out.println("Sie sind nicht eingeloggt.");
            return;
        }

        if (dlcs.isEmpty()) {
            System.out.println("Keine DLCs verfügbar, die hinzugefügt werden können.");
            return;
        }

        System.out.println("Verfügbare DLCs:");
        for (DLC dlc : dlcs) {
            System.out.println("- " + dlc.getDlcName() + " (ID: " + dlc.getId() + ", Spiel: " + dlc.getGameTitle() + ")");
        }

        System.out.println("Bitte geben Sie den Namen des DLCs ein, den Sie hinzufügen möchten:");
        String dlcName = this.scanner.nextLine();

        DLC selectedDlc = LookupService.findDlcByName(dlcs, dlcName);
        if (selectedDlc == null) {
            System.out.println("DLC mit dieser Namen wurde nicht gefunden.");
            return;
        }

        if (activeUser.getOwnedDLCsIds().contains(selectedDlc.getId())) {
            System.out.println("Dieses DLC ist bereits in Ihrer Bibliothek.");
            return;
        }

        Game baseGame = LookupService.findGameByTitle(games, selectedDlc.getGameTitle());
        if (baseGame != null && !activeUser.getOwnedGamesIds().contains(baseGame.getId())) {
            System.out.println("Sie müssen zuerst das Hauptspiel besitzen: " + baseGame.getTitel());
            return;
        }

        activeUser.addOwnedDLCs(selectedDlc.getId());
        if (!saveData()) {
            System.out.println("Hinweis: DLC wurde hinzugefügt, konnte aber nicht gespeichert werden.");
            return;
        }

        System.out.println("DLC erfolgreich hinzugefügt: " + selectedDlc.getDlcName());
    }

    /**
     * Erstellt ein neues DLC über Konsoleneingaben.
     *
     * @return neu erstelltes DLC
     */
    public DLC createDLC() {
        DLC dlc = new DLC(UUID.randomUUID().toString());

        System.out.println("Bitte geben Sie den Namen des DLCs ein");
        dlc.setDlcName(this.scanner.nextLine());

        System.out.println("Bitte geben Sie den Titel des zugehörigen Spiels ein");
        dlc.setGameTitle(this.scanner.nextLine());

        System.out.println("Bitte geben Sie die Beschreibung des DLCs ein");
        dlc.setDescription(this.scanner.nextLine());

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
