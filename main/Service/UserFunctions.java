package Service;

import Entites.DLC;
import Entites.Game;
import Entites.User;

import static Service.FunctionService.*;

public class UserFunctions {
    /**
     * Zeigt das Benutzerverwaltungsmenü an.
     */
    public static void userAdministration() {
        while (true) {
            System.out.println("Die Optionen sind:"
                    + ", Benutzerinformationen anzeigen (i)"
                    + ", Benutzerinformationen aktualisieren (u)"
                    + ", Benutzer löschen (d)"
                    + ", Zurück zum Hauptmenü (e)"
            );
            String choice = scanner.nextLine();
            switch (choice.toLowerCase()) {
                case "i": // Benutzerinformationen anzeigen
                    displayUserInformation();
                    break;
                case "u": // Benutzerinformationen aktualisieren
                    updateUserName();
                    break;
                case "d": // Benutzer löschen
                    if (deleteUser()) break;
                    return;
                case "e": // Zurück zum Hauptmenü
                    return;
                default:
                    System.out.println("Ungültige Eingabe. Bitte versuchen Sie es erneut." + choice);
            }
        }
    }

    private static boolean deleteUser() {
        System.out.println("Bist du dir sicher? tippe 'remove'");
        if (!scanner.nextLine().equalsIgnoreCase("remove")) {
            System.out.println("Benutzerlöschung abgebrochen.");
            return true;
        }
        System.out.println("Benutzer '" + activeUser.getUsername() + "' wird gelöscht...");
        users.remove(activeUser);
        if (!saveData()) {
            System.out.println("Hinweis: Benutzer wurde gelöscht, konnte aber nicht gespeichert werden.");
        }
        activeUser = null;
        return false;
    }

    private static void updateUserName() {
        System.out.println("Bitte neuen Benutzernamen eingeben:");
        String newUsername = scanner.nextLine();
        if (newUsername.trim().isEmpty()) {
            System.out.println("Ungültiger Benutzername. Nicht aktualisiert.");
            return;
        }
        System.out.println("- Benutzername: " + newUsername);
    }

    private static void displayUserInformation() {
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
    }

    /**
     * Erstellt einen neuen Benutzer anhand der Eingabe des Benutzernamens.
     *
     * @return neu erstellter Benutzer
     */
    public static User createUser() {
        String username = "";
        while (true) {
            System.out.println("Bitte Ihren Benutzernamen eingeben");
            username = scanner.nextLine();

            if (username.trim().isEmpty()) {
                continue;
            }

            if (username.trim().length() < 3) {
                System.out.println("Der Benutzername muss mindestens 3 Zeichen lang sein. Bitte versuchen Sie es erneut.");
                continue;
            }
            boolean isUnique = true;
            for (User user: users) {
                if (username.equals(user.getUsername())) {
                    System.out.println("Dieser Benutzername ist bereits vergeben. Bitte wählen Sie einen anderen.");
                    isUnique = false;
                    break;
                }
            }

            if (isUnique) {
                break;
            }
        }

        String email = "";
        System.out.println("Bitte Ihren Email eingeben");
        while (true) {
            email = scanner.nextLine();

            if (email.trim().isEmpty()) {
                System.out.println("Bitte Eingabe Email eingeben.");
                continue;
            }

            int atIndex = email.indexOf('@');
            int dotIndex = email.lastIndexOf('.');

            if (atIndex > 0 && dotIndex > atIndex + 1 && dotIndex < email.length() - 1) {
                break;
            }

            System.out.println("Ungültige E-Mail-Adresse. Bitte versuchen Sie es erneut.");
        }

        String password = "";
        System.out.println("Bitte Ihren Passwort eingeben");
        while (true) {
            password = scanner.nextLine();

            if (password.trim().isEmpty()) {
                System.out.println("Bitte Eingabe Passwort eingeben.");
                continue;
            }

            if (password.length() < 8) {
                System.out.println("Das Passwort muss mindestens 8 Zeichen lang sein. Bitte versuchen Sie es erneut.");
                continue;
            }

            break;
        }

        return new User(username, email, password);
    }

    /**
     * Initialisiert die Login-Sitzung und ermöglicht dem Benutzer, sich entweder anzumelden oder zu registrieren.
     *
     * @return 0, wenn der Benutzer das Programm verlassen möchte, -1 bei erfolgreichem Login oder Registrierung, oder 1 bei einem unerwarteten Fehler
     */
    public static int initializeUser() {
        while (activeUser == null) {
            System.out.println("\n\nWillkommen im Game Store!"
                    + "\nBitte melden Sie sich an, um fortzufahren."
                    + "\nSie können sich anmelden oder registrieren (l für Login, r für Registrierung, e für verlassen)\n");

            String choice = scanner.nextLine();
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
    public static User loginUser() {
        if (users.isEmpty()) {
            System.out.println("Es sind noch keine Benutzer registriert. Bitte registrieren Sie sich zuerst.");
            return null;
        }

        System.out.println("Bitte Benutzernamen für den Login eingeben:");
        String username = scanner.nextLine();

        User foundUser = LookupService.findUserByUsername(users, username);
        if (foundUser == null) {
            System.out.println("Benutzer nicht gefunden. Try again.");
            return null;
        }

        System.out.println("Bitte Passwort für den Benutzer eingeben:");
        while (activeUser == null) {
            String password = scanner.nextLine();

            if (foundUser.getPassword().equals(password)) {
                break;
            }

            System.out.println("Falsches Passwort. Try again:");
        }

        System.out.println("Login erfolgreich. Willkommen, " + foundUser.getUsername() + "!");
        return foundUser;
    }
}
