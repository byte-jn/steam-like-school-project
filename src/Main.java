import Service.FunctionService;

/**
 * Einstiegspunkt der Anwendung.
 *
 * @author Jannis Lauer (jannis280@outlook.de)
 */
public class Main {
    /**
     * Startet die Konsolenanwendung und übergibt den Exit-Code.
     *
     * @param args Kommandozeilenargumente
     */
    public static void main(String[] args) {
        new FunctionService().loop();
    }
}