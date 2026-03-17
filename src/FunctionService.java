import java.util.Scanner;

public class FunctionService {
    public Scanner scanner;

    public FunctionService() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Initialisiert die Login-Sitzung und ermöglicht dem Benutzer, sich entweder anzumelden oder zu registrieren.
     */
    public User initial() {
        System.out.println("Willkommen zum Game Store!" + "\nBitte melden Sie sich an, um fortzufahren." + "\nyou can login or registrieren (l for login, r for registrieren)");
        String choice = this.scanner.next();
        if (choice.equalsIgnoreCase("l")) {
            searchUser();
            return initial();
        } else if (choice.equalsIgnoreCase("r")) {
            return createUser();
        } else {
            System.out.println("Ungültige Eingabe. Bitte versuchen Sie es erneut.");
            return initial();
        }
    }

    private void searchUser() {
        // TODO: Implementieren Sie die Logik, um einen Benutzer zu suchen und sich anzumelden.
        System.out.println("Die Login-Funktionalität ist noch nicht implementiert. Bitte registrieren Sie sich zuerst.");
    }

    /**
     * Erstellt einen neuen Benutzer, indem er den Benutzernamen, Vornamen und Nachnamen abfragt. Der Benutzername ist erforderlich, während Vorname und Nachname optional sind.
     */
    public User createUser() {
        System.out.println("Bitte ihren Vornamen eingeben");
        String vorname = this.scanner.next();

        System.out.println("Bitte ihren Nachnamen eingeben");
        String nachname = this.scanner.next();

        User user = new User(vorname, nachname);

        System.out.println("Bitte Ihren Vornamen eingeben, wenn Sie möchten, ansonsten können Sie diesen Schritt überspringen");
        user.setFirstname(this.scanner.next());

        System.out.println("Bitte Ihren Nachnamen eingeben, wenn Sie möchten, ansonsten können Sie diesen Schritt überspringen");
        user.setLastname(this.scanner.next());

        return user;
    }
}
