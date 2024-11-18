package org.example.movietime.repositories;

import org.example.movietime.entities.Cliente;
import org.example.movietime.entities.Ordine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface OrdineRepository extends JpaRepository<Ordine,Integer> {

    @Query("SELECT o FROM Ordine o, Carrello c WHERE o.idOrdine=:idOrdine AND o.carrello.idCarrello=c.idCarrello AND c.cliente.idCliente=:idCliente")
    Optional<Ordine> findByIdAndCliente(@Param("idOrdine") int idOrdine,@Param("idCliente") int idCliente);

    @Query("SELECT o FROM Ordine o WHERE o.carrello.cliente.idCliente = :idCliente")
    Page<Ordine> findAllByCliente(@Param("idCliente") int idCliente, Pageable pageable);

    @Query("SELECT o FROM Ordine o, Carrello c WHERE o.carrello.idCarrello=c.idCarrello AND c.cliente.idCliente=:idCliente ORDER BY o.dataOrdine DESC")
    Page<Ordine> findAllOrderByDataDesc(@Param("idCliente") int idCliente, Pageable pageable);

    @Query("SELECT o FROM Ordine o, Carrello c WHERE o.carrello.idCarrello=c.idCarrello AND c.cliente.idCliente=:idCliente ORDER BY o.dataOrdine ASC")
    Page<Ordine> findAllOrderByDataAsc(@Param("idCliente") int idCliente, Pageable pageable);


}
