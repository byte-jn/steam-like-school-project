package Tests;

import Entites.Games;
import Service.FunctionService;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Calendar;
import static org.junit.jupiter.api.Assertions.*;

class FunctionTest {

    @Test
    void test() {
        // 1. Eingaben simulieren (Titel, Beschreibung, Preis, Jahr, Monat, Tag)
        // Wichtig: Nutze das Format, das dein System/Scanner erwartet (z.B. Punkt oder Komma bei Double)
        String simulatedInput = "l\ntestUser1\ns\nc\nElden Ring\nEin Open-World-RPG\n59,99\n2024\n12\n24\ne\ne";
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
