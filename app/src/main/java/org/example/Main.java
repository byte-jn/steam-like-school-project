public class Main {
    public static void main(String[] args) {
        FunctionService session = new FunctionService();

        System.exit(
                session.loop()
        );
    }
}