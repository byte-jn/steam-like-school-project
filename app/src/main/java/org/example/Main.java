package org.example;

import org.example.entities.User;
import org.example.services.FunctionService;

public class Main {
    public static void main(String[] args) {
        FunctionService session = new FunctionService();

        User user = session.initializeUser();
    }
}