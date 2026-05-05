package org.example.repositories;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;
import org.example.entities.Games;

import java.util.List;

@Repository
public interface GamesRepository extends JpaRepository<Games, String> {

    List<Games> findAll();
}
