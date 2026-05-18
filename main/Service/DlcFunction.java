package Service;

import Entites.DLC;
import Entites.Game;
import Entites.User;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import static Service.FunctionService.*;

public class DlcFunction {
    /**
     * Zeigt das DLC-Menü an und ermöglicht Erstellen, Hinzufügen und Auflisten.
     */
    public static void initializeDLC() {
        while (true) {
            System.out.println("Willkommen im DLC-Store!"
                    + "\nBitte wählen Sie: DLC hinzufügen oder suchen (c für erstellen, a für hinzufügen, r für entfernen, l für auflisten, e für verlassen)");
            String choice = scanner.nextLine();
            if (choice.equalsIgnoreCase("a")) {
                addDLC();
            } else if (choice.equalsIgnoreCase("c")) {
                dlcs.add(createDLC());
                if (!saveData()) {
                    System.out.println("Hinweis: DLC konnte nicht gespeichert werden.");
                }
            }
            else if (choice.equalsIgnoreCase("r")) {
                if (removeDlc()) return;
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

    private static boolean removeDlc() {
        if (dlcs.isEmpty()) {
            System.out.println("Keine DLCs verfügbar, die entfernt werden können.");
            return true;
        }

        System.out.println("Verfügbare DLCs:");
        for (DLC dlc : dlcs) {
            System.out.println("- " + dlc.getDlcName() + " (ID: " + dlc.getId() + ", Spiel: " + dlc.getGameTitle() + ")");
        }

        System.out.println("Bitte geben Sie die Namen des DLCs ein, das Sie entfernen möchten:");
        String dlcName = scanner.nextLine();

        DLC selectedDlc = LookupService.findDlcByName(dlcs, dlcName);
        if (selectedDlc == null) {
            System.out.println("DLC mit dieser Namen wurde nicht gefunden.");
            return true;
        }

        dlcs.remove(selectedDlc);
        if (!saveData()) {
            System.out.println("Hinweis: DLC wurde entfernt, konnte aber nicht gespeichert werden.");
            return true;
        }

        for (User u : users) {
            u.removeDlc(selectedDlc.getId());
        }

        System.out.println("DLC erfolgreich entfernt: " + selectedDlc.getDlcName());
        return false;
    }

    /**
     * Fügt ein vorhandenes DLC per ID zur Bibliothek des aktiven Benutzers hinzu.
     */
    private static void addDLC() {
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
        String dlcName = scanner.nextLine();

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
    public static DLC createDLC() {
        System.out.println("Bitte geben Sie den Namen des DLCs ein");
        String dlcName = scanner.nextLine();
        // Überprüfen, ob DLC-Name bereits existiert
        if (LookupService.findDlcByName(dlcs, dlcName) != null) {
            System.out.println("Ein DLC mit diesem Namen existiert bereits!");
            return null;
        }
        DLC dlc = new DLC(UUID.randomUUID().toString());
        dlc.setDlcName(dlcName);

        System.out.println("Bitte geben Sie den Titel des zugehörigen Spiels ein");
        dlc.setGameTitle(scanner.nextLine());

        System.out.println("Bitte geben Sie die Beschreibung des DLCs ein");
        dlc.setDescription(scanner.nextLine());

        while (true) {
            try {
                System.out.println("Bitte geben Sie den Preis des DLCs ein (z. B. 19.99)");
                dlc.setPrice(scanner.nextDouble());
            }
            catch (Exception e) {
                System.out.println("Ungültige Eingabe für Preis. Bitte versuchen Sie es erneut.\n");
                continue;
            }
            break;
        }

        int year;
        int month;
        int day;
        Calendar calendar = Calendar.getInstance();
        while (true) {
            try {
                System.out.println("Bitte geben Sie das Erscheinungsjahr des DLCs ein (z. B. 2026)");
                year = scanner.nextInt();
                System.out.println("Bitte geben Sie den Erscheinungsmonat des DLCs ein (1-12)");
                month = scanner.nextInt();
                System.out.println("Bitte geben Sie den Erscheinungstag des DLCs ein (1-31)");
                day = scanner.nextInt();

                calendar.set(year, month - 1, day, 0, 0, 0); // month - 1 ist korrekt, aber Warnung wegen Calendar-Konstanten
                calendar.set(Calendar.MONTH, month - 1); // explizit Calendar.MONTH setzen
                calendar.set(Calendar.MILLISECOND, 0);
                Date date = calendar.getTime();

                dlc.setReleaseDate(date);
            } catch (Exception e) {
                System.out.println("Ungültige Eingabe für Datum. Bitte versuchen Sie es erneut.\n");
                calendar.clear();
                continue;
            }
            break;
        }

        return dlc;
    }
}
