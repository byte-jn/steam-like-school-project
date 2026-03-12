public class Main {
    public static void main(String[] args) {
        Login session = new Login();

        session.initial();

        // Hier könnte anstelle von choiceloop() eine andere Methode aufgerufen werden,
        // um die Benutzerinteraktion zu starten
        session.choiceloop();
    }
}