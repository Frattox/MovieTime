package org.example.movietime.repositories;

import org.example.movietime.entities.Carrello;
import org.example.movietime.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarrelloRepository extends JpaRepository<Carrello, Integer> {

    @Query("SELECT c FROM Carrello c WHERE c.cliente.idCliente=?1")
    Optional<Carrello> findByIdCliente(int idCliente);

    boolean existsByCliente(Cliente cliente);
}
