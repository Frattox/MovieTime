package org.example.movietime.repositories;

import org.example.movietime.entities.Cliente;
import org.example.movietime.entities.MetodoPagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

//Con l'annotazione repository si ottengono anche i metodi di base CRUD (Create, Read, Update, Delete)
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    boolean existsByEmail(String email);
    Optional<Cliente> findByEmail(String email);
}
