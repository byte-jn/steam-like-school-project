package org.example.repositories;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;
import org.example.entities.Dlc;

import java.util.List;

@Repository
public interface DlcRepository extends JpaRepository<Dlc, String> {

    List<Dlc> findAll();
}
