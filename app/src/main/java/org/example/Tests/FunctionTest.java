package Tests;

import Service.FunctionService;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

class FunctionTest {

    @Test
    void test() {
        // 1. Eingaben simulieren (Titel, Beschreibung, Preis, Jahr, Monat, Tag)
        // Wichtig: Nutze das Format, das dein System/Scanner erwartet (z.B. Punkt oder Komma bei Double)
        String simulatedInput = "r\ntest\n"+ // register test user
                "l\n"+ // logout user
                "l\ntest\n"+ // login user test
                "s\nc\nTestGame\nzum Testen\n1\n2000\n1\n1\n"+ // create game testGame
                "h\na\nTestGame\nl\ne\n"+ // add Test game to user
                "d\nc\nTestErweiterung\nTestGame\nzum Testen\n1\n2000\n1\n1\n"+ // create dlc testErweiterung
                "a\nTestErweiterung\nl\ne\n"+ // add testErweiterung to user
                "s\nr\nTestGame\ne\n"+
                "d\nr\nTestErweiterung\ne\n"+
                "u\ni\nu\ntest\nd\nremove\n"+
                "e"; // exit system
        InputStream originalIn = System.in; // Backup vom originalen System.in

        try {
            System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

            // 2. Methode ausführen (angenommen, die Klasse heißt GamesService)
            FunctionService service = new FunctionService();
            int exitCode = service.loop();

        } finally {
            // 4. System.in wieder zurücksetzen
            System.setIn(originalIn);
        }
    }

}
