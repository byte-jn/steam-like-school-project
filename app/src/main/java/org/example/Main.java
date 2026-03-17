package org.example;

public class Main {
    public static void main(String[] args) {
        FunctionService session = new FunctionService();

        User user = session.initializeUser();
    }
}