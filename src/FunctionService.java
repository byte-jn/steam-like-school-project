import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.util.UUID;

public class FunctionService {
    public Scanner scanner;

    public FunctionService() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Initialisiert die Login-Sitzung und ermöglicht dem Benutzer, sich entweder anzumelden oder zu registrieren.
     */
    public User initializeUser() {
        System.out.println("Willkommen im Game Store!"
                + "\nBitte melden Sie sich an, um fortzufahren."
                + "\nSie können sich anmelden oder registrieren (l für Login, r für Registrierung)");
        String choice = this.scanner.next();
        if (choice.equalsIgnoreCase("l")) {
            searchUser();
            return initializeUser();
        } else if (choice.equalsIgnoreCase("r")) {
            return createUser();
        } else {
            System.out.println("Ungültige Eingabe. Bitte versuchen Sie es erneut.");
            return initializeUser();
        }
    }

    private void searchUser() {
        // TODO: Implementieren Sie die Logik, um einen Benutzer zu suchen und sich anzumelden.
        System.out.println("Die Login-Funktionalität ist noch nicht implementiert. Bitte registrieren Sie sich zuerst.");
    }

    /**
     * Erstellt einen neuen Benutzer, indem Vorname und Nachname abgefragt werden.
     */
    public User createUser() {
        System.out.println("Bitte Ihren Vornamen eingeben");
        String vorname = this.scanner.next();

        System.out.println("Bitte Ihren Nachnamen eingeben");
        String nachname = this.scanner.next();

        User user = new User(vorname, nachname);

        System.out.println("Bitte Ihren Vornamen eingeben, wenn Sie möchten; ansonsten können Sie diesen Schritt überspringen");
        user.setFirstname(this.scanner.next());

        System.out.println("Bitte Ihren Nachnamen eingeben, wenn Sie möchten; ansonsten können Sie diesen Schritt überspringen");
        user.setLastname(this.scanner.next());

        return user;
    }

    public Games initializeGames() {
        System.out.println("Willkommen im Game Store!"
                + "\nBitte melden Sie sich an, um fortzufahren."
                + "\nSie können sich anmelden oder registrieren (l für Login, r für Registrierung)");
        String choice = this.scanner.next();
        if (choice.equalsIgnoreCase("l")) {
            searchGames();
            return initializeGames();
        } else if (choice.equalsIgnoreCase("r")) {
            return createGames();
        } else {
            System.out.println("Ungültige Eingabe. Bitte versuchen Sie es erneut.");
            return initializeGames();
        }
    }

    private void searchGames() {
        // TODO: Implementieren Sie die Logik, um ein Spiel zu suchen.
        System.out.println("Die Suchfunktion ist noch nicht implementiert. Bitte legen Sie zuerst ein Spiel an.");
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
        calendar.set(year, month - 1, day, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date date = calendar.getTime();

       games.setReleaseDate(date);

        return games;
    }
}
