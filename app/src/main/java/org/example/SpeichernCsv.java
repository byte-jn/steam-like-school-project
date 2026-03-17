
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
public class SpeichernCsv {

    public SpeichernCsv(){}

    public static void SpeicherCsv(String vname, String nname){
        String pfad = "U:\\Benutzer\\Dateien.csv";
        try (FileWriter fw = new FileWriter(pfad, true);
             PrintWriter pw = new PrintWriter(fw)) {
            pw.println(vname + ";" + nname );
        } catch (IOException e) {
            System.err.println("Fehler beim Schreiben der Datei: " + e.getMessage());
        }
    }
}
