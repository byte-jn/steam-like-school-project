package Service;

import Entites.Game;
import Entites.User;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import static Service.FunctionService.*;

public class GameFunctions {
    /**
     * Zeigt das Spielmenü an und ermöglicht Erstellen, Hinzufügen und Auflisten.
     */
    public static void initializeGames() {
        while (true) {
            System.out.println("Willkommen im Game Store!"
                    + "\nBitte wählen Sie: Game hinzufügen oder suchen (c für Erstellen, a für hinzufügen, r für entfernen, l für auflisten, e für exit)");
            String choice = scanner.nextLine();
            if (choice.equalsIgnoreCase("a")) {
                addGames();
            } else if (choice.equalsIgnoreCase("c")) {
                games.add(createGames());
                if (!saveData()) {
                    System.out.println("Hinweis: Spiel konnte nicht gespeichert werden.");
                }
            } else if (choice.equalsIgnoreCase("r")) {
                if (removeGame()) return;
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

    private static boolean removeGame() {
        if (games.isEmpty()) {
            System.out.println("Keine Spiele verfügbar, die entfernt werden können.");
            return true;
        }

        System.out.println("Verfügbare Spiele:");
        for (Game game : games) {
            System.out.println("- " + game.getTitel() + " (ID: " + game.getId() + ")");
        }

        System.out.println("Bitte geben Sie die Namen des Spiels ein, das Sie entfernen möchten:");
        String gameName = scanner.nextLine();

        Game selectedGame = LookupService.findGameByName(games, gameName);
        if (selectedGame == null) {
            System.out.println("Spiel mit dieser Namen wurde nicht gefunden.");
            return true;
        }

        games.remove(selectedGame);
        if (!saveData()) {
            System.out.println("Hinweis: Spiel wurde entfernt, konnte aber nicht gespeichert werden.");
            return true;
        }

        for (User u : users) {
            u.removeGame(selectedGame.getId());
        }

        System.out.println("Spiel erfolgreich entfernt: " + selectedGame.getTitel());
        return false;
    }

    /**
     * Fügt ein vorhandenes Spiel per ID zur Bibliothek des aktiven Benutzers hinzu.
     */
    public static void addGames() {
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
        String gameName = scanner.nextLine();

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
    public static Game createGames() {
        System.out.println("Bitte geben Sie den Titel des Spiels ein");
        String titel = scanner.nextLine();
        // Überprüfen, ob Spieltitel bereits existiert
        if (LookupService.findGameByTitle(games, titel) != null) {
            System.out.println("Ein Spiel mit diesem Titel existiert bereits!");
            return null;
        }
        Game games = new Game(UUID.randomUUID().toString());
        games.setTitel(titel);

        System.out.println("Bitte geben Sie die Beschreibung des Spiels ein");
        games.setDescription(scanner.nextLine());

        while (true) {
            try {
                System.out.println("Bitte geben Sie den Preis des Spiels ein (z. B. 59,99)");
                games.setPrice(scanner.nextDouble());
            } catch (Exception ex) {
                System.out.println("Ungültige Eingabe für Preis. Bitte versuchen Sie es erneut.\n");
                continue;
            }
            break;
        }

        int year = 0;
        int month = 0;
        int day = 0;
        Calendar calendar = Calendar.getInstance();
        while (true) {
            try {
                System.out.println("Bitte geben Sie das Erscheinungsjahr des Spiels ein (z. B. 2024)");
                year = scanner.nextInt();
                System.out.println("Bitte geben Sie den Erscheinungsmonat des Spiels ein (1-12)");
                month = scanner.nextInt();
                System.out.println("Bitte geben Sie den Erscheinungstag des Spiels ein (1-31)");
                day = scanner.nextInt();

                calendar.set(year, month - 1, day, 0, 0, 0); // month - 1 ist korrekt, aber Warnung wegen Calendar-Konstanten
                calendar.set(Calendar.MONTH, month - 1); // explizit Calendar.MONTH setzen
                calendar.set(Calendar.MILLISECOND, 0);
                Date date = calendar.getTime();

                games.setReleaseDate(date);
            } catch (Exception e) {
                System.out.println("Ungültige Eingabe für Datum. Bitte versuchen Sie es erneut.\n");
                calendar.clear();
                continue;
            }
            break;
        }

        return games;
    }

}
