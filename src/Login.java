import java.util.Scanner;

public class Login {
    public Scanner scanner;
    public User user;

    public Login() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Initialisiert die Login-Sitzung und ermöglicht dem Benutzer, sich entweder anzumelden oder zu registrieren.
     */
    public void initial() {
        System.out.println("Willkommen zum Game Store!" + "\nBitte melden Sie sich an, um fortzufahren." + "\nyou can login or registrieren (l for login, r for registrieren)");
        String choice = this.scanner.next();
        if (choice.equalsIgnoreCase("l")) {
            searchUser();
        } else if (choice.equalsIgnoreCase("r")) {
            createUser();
        } else {
            System.out.println("Ungültige Eingabe. Bitte versuchen Sie es erneut.");
            initial();
        }
    }

    private void searchUser() {
        // TODO: Implementieren Sie die Logik, um einen Benutzer zu suchen und sich anzumelden.
        System.out.println("Die Login-Funktionalität ist noch nicht implementiert. Bitte registrieren Sie sich zuerst.");
    }

    /**
     * Erstellt einen neuen Benutzer, indem er den Benutzernamen, Vornamen und Nachnamen abfragt. Der Benutzername ist erforderlich, während Vorname und Nachname optional sind.
     */
    public void createUser() {
        System.out.println("Bitte ihren Vornamen eingeben");
        String vorname = this.scanner.next();

        System.out.println("Bitte ihren Nachnamen eingeben");
        String nachname = this.scanner.next();

        User user = new User(vorname, nachname);

        System.out.println("Bitte Ihren Vornamen eingeben, wenn Sie möchten, ansonsten können Sie diesen Schritt überspringen");
        user.setVorname(this.scanner.next());

        System.out.println("Bitte Ihren Nachnamen eingeben, wenn Sie möchten, ansonsten können Sie diesen Schritt überspringen");
        user.setNachname(this.scanner.next());

        this.user = user;
    }

    public void choiceloop() {
        while (true) {
            System.out.println("Was möchten Sie tun? (1: Spiel hinzufügen, 2: DLC hinzufügen, 3: Beenden)");
            int choice = new java.util.Scanner(System.in).nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Funktion zum Hinzufügen eines Spiels ist noch nicht implementiert.");
                    break;
                case 2:
                    System.out.println("Funktion zum Hinzufügen eines DLCs ist noch nicht implementiert.");
                    break;
                case 3:
                    System.out.println("Programm wird beendet. Auf Wiedersehen!");
                    return;
                default:
                    System.out.println("Ungültige Eingabe. Bitte versuchen Sie es erneut.");
                    break;
            }
        }
    }
}
