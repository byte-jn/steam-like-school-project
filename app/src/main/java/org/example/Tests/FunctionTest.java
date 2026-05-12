package Tests;

import Service.FunctionService;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

class FunctionTest {

    @Test
    void test() {
        String simulatedInput = "l\n48730523894325327509324857438593275032957testUser43298573259834275943275320958743252098576328749543\n" + // try login, should fail because user doesn't exist yet
                "r\n48730523894325327509324857438593275032957testUser43298573259834275943275320958743252098576328749543\ntest@example.com\nigdojrjgirb49ut094utbnjreIUI)(/G=)(/&/ZU\n"+ // register test user
                "l\n"+ // logout user
                "l\n48730523894325327509324857438593275032957testUser43298573259834275943275320958743252098576328749543\nigdojrjgirb49ut094utbnjreIUI)(/G=)(/&/ZU\n"+ // login user test
                "s\nc\nTestGame\nzum Testen\n1\n2000\n1\n1\n"+ // create game testGame
                "h\na\nTestGame\nl\ne\n"+ // add Test game to user
                "d\nc\nTestErweiterung\nTestGame\nzum Testen\n1\n2000\n1\n1\n"+ // create dlc testErweiterung
                "a\nTestErweiterung\nl\ne\n"+ // add testErweiterung to user
                "s\nr\nTestGame\ne\n"+
                "d\nr\nTestErweiterung\ne\n"+
                "u\ni\nu\n48730523894325327509324857438593275032957testUser43298573259834275943275320958743252098576328749543\nd\nremove\n"+
                "e"; // exit system
        InputStream originalIn = System.in; // Backup des originalen System.in

        try {
            System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

            // Service starten und Testablauf simulieren
            FunctionService service = new FunctionService();
            int exitCode = service.loop();

        } finally {
            // Ursprünglichen System.in wiederherstellen und mit Fehlercode beenden
            System.setIn(originalIn);
        }
    }

}
