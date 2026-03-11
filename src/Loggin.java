import java.util.List;
import java.util.Scanner;
public class Loggin {

    public Loggin(){

    }

    public void Einfuegen(){
        Scanner input = new Scanner(System.in);

        System.out.println("Bitte ihre Vorname eingeben");
        String vName = input.next();

        System.out.println("Bitte ihre Nachname eingeben");
        String nName = input.next();
        new Spieler(vName, nName);
        SpeichernCsv.SpeicherCsv(vName, nName);
    }
}
