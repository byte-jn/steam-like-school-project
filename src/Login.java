import java.util.Scanner;

public class Login {

    public Login() {
    }

    public void inputName() {
        Scanner input = new Scanner(System.in);

        System.out.println("Bitte Ihren Vornamen eingeben");
        String vorname = input.next();

        System.out.println("Bitte Ihren Nachnamen eingeben");
        String nachname = input.next();

        new Spieler(vorname, nachname);
        SpeichernCsv.speichereCsv(vorname, nachname);
    }
}
