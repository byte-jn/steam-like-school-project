package org.example;

import com.google.inject.Injector;
import org.example.dtos.UserDto;
import org.example.services.FunctionService;
import org.example.utils.AppInjector;
import org.example.utils.HibernateUtil;

public class Main {

    public static void main(String[] args) {
        Injector injector = AppInjector.get();

        FunctionService service = injector.getInstance(FunctionService.class);

        UserDto user = service.initializeUser();
        service.mainMenu(user);

        HibernateUtil.shutdown();
    }
}
