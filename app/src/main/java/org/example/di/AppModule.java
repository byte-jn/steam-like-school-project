package org.example.di;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
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

public class AppModule extends AbstractModule {

    @Override
    protected void configure() {
        // --- Mappers ---
        bind(UserMapper.class).in(Singleton.class);
        bind(GamesMapper.class).in(Singleton.class);
        bind(DlcMapper.class).in(Singleton.class);

        // --- Repositories ---
        bind(UserRepository.class).in(Singleton.class);
        bind(GamesRepository.class).in(Singleton.class);
        bind(DlcRepository.class).in(Singleton.class);

        // --- Services ---
        bind(UserService.class).in(Singleton.class);
        bind(GamesService.class).in(Singleton.class);
        bind(DlcService.class).in(Singleton.class);
        bind(FunctionService.class).in(Singleton.class);
    }

    /**
     * Provides the single shared SessionFactory for the whole application.
     * Guice calls this once and caches the result because of @Singleton.
     */
    @Provides
    @Singleton
    public SessionFactory provideSessionFactory() {
        return HibernateUtil.getSessionFactory();
    }
}
