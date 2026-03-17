package org.example;

import org.example.dtos.UserDto;
import org.example.mappers.DlcMapper;
import org.example.mappers.GamesMapper;
import org.example.mappers.UserMapper;
import org.example.repositories.DlcRepository;
import org.example.repositories.GamesRepository;
import org.example.repositories.UserRepository;
import org.example.services.DlcService;
import org.example.services.FunctionService;
import org.example.services.GamesService;
import org.example.services.UserService;
import org.example.utils.HibernateUtil;
import org.hibernate.SessionFactory;

public class Main {

    public static void main(String[] args) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        UserRepository userRepository = new UserRepository(sessionFactory);
        GamesRepository gamesRepository = new GamesRepository(sessionFactory);
        DlcRepository dlcRepository = new DlcRepository(sessionFactory);

        UserService userService = new UserService(userRepository, new UserMapper());
        GamesService gamesService = new GamesService(gamesRepository, new GamesMapper());
        DlcService dlcService = new DlcService(dlcRepository, new DlcMapper());

        FunctionService service = new FunctionService(userService, gamesService, dlcService);

        UserDto user = service.initializeUser();
        service.mainMenu(user);

        HibernateUtil.shutdown();
    }
}
