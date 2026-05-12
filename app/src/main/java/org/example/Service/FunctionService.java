package Service;

import Entites.DLC;
import Entites.Game;
import Entites.User;

import java.util.ArrayList;
import java.util.Scanner;

import static Service.DlcFunction.initializeDLC;
import static Service.GameFunctions.initializeGames;
import static Service.UserFunctions.initializeUser;
import static Service.UserFunctions.userAdministration;

/**
 * Steuert den gesamten Konsolenablauf der Anwendung inklusive Menüs, Login und Benutzeraktionen.
 *
 * @author Jannis Lauer (jannis280@outlook.de)
 */
public class FunctionService {
    public static Scanner scanner;
    public static CsvDataService csvDataService;
    public static User activeUser;
    public static ArrayList<User> users;
    public static ArrayList<Game> games;
    public static ArrayList<DLC> dlcs;
    public static ArrayList<String> choices;

    /**
     * Initialisiert Eingabe, Datenservice und In-Memory-Listen.
     */
    public FunctionService() {
        scanner = new Scanner(System.in);
        csvDataService = new CsvDataService();
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
                System.out.println("\n");
            }
            // Angabe der Auswahlmöglichkeiten für den Benutzer
            System.out.print("Bitte wählen Sie: ");
            choices.forEach(System.out::print);

            String choice = scanner.nextLine();
            switch (choice.toLowerCase()) {
                case "e":
                    System.out.println("\n");
                    if (!saveData()) {
                        System.out.println("Hinweis: Daten konnten vor dem Beenden nicht gespeichert werden.");
                        return 1; // Beendet mit Fehlercode, wenn Speichern fehlschlägt
                    }
                    System.out.println("Vielen Dank für Ihren Besuch im Game Store. Auf Wiedersehen!");
                    return 0; // Beendet die Schleife und damit das Programm
                case "l":
                    System.out.println("\n");
                    if (!saveData()) {
                        System.out.println("Hinweis: Daten konnten beim Abmelden nicht gespeichert werden.");
                    }
                    System.out.println("Sie wurden erfolgreich abgemeldet.");
                    activeUser = null; // Setzt den Benutzer auf null, um die Anmeldung zurückzusetzen
                    break;
                case "s":
                    System.out.println("\n");
                    initializeGames();
                    System.out.println("\n");
                    break;
                case "d":
                    System.out.println("\n");
                    initializeDLC();
                    System.out.println("\n");
                    break;
                case "u":
                    System.out.println("\n");
                    userAdministration();
                    System.out.println("\n");
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
    public static boolean saveData() {
        return csvDataService.saveAll(users, games, dlcs);
    }
}
