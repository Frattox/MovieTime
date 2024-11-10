package org.example.movietime.exceptionHandler.repositories;

import org.example.movietime.entities.Carrello;
import org.example.movietime.entities.DettaglioCarrello;
import org.example.movietime.entities.Film;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DettaglioCarrelloRepository extends JpaRepository<DettaglioCarrello,Integer> {


    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT d FROM DettaglioCarrello d WHERE d.idDettaglioCarrello = :id AND d.carrello.cliente.idCliente = :idCliente")
    Optional<DettaglioCarrello> findByIdAndClienteWithLock(@Param("id") int idDettaglioCarrello, @Param("idCliente") int idCliente);

    Optional<DettaglioCarrello> findByFilmAndCarrello(Film film, Carrello carrello);

    Optional<DettaglioCarrello> findByIdDettaglioCarrelloAndCarrello(int idDettaglioCarrello, Carrello carrello);

    @Query("SELECT d FROM DettaglioCarrello d WHERE d.carrello=:carrello ORDER BY d.film.titolo DESC")
    Page<DettaglioCarrello> findAllOrderByTitoloDesc(@Param("carrello") Carrello carrello, Pageable pageable);

    @Query("SELECT d FROM DettaglioCarrello d WHERE d.carrello=:carrello ORDER BY d.film.titolo ASC")
    Page<DettaglioCarrello> findAllOrderByTitoloAsc(@Param("carrello") Carrello carrello, Pageable pageable);

    @Query("SELECT d FROM DettaglioCarrello d WHERE d.carrello=:carrello ORDER BY d.film.prezzo DESC")
    Page<DettaglioCarrello> findAllOrderByPrezzoDesc(@Param("carrello") Carrello carrello, Pageable pageable);

    @Query("SELECT d FROM DettaglioCarrello d WHERE d.carrello=:carrello ORDER BY d.film.prezzo ASC")
    Page<DettaglioCarrello> findAllOrderByPrezzoAsc(@Param("carrello") Carrello carrello, Pageable pageable);

    @Query("SELECT d FROM DettaglioCarrello d WHERE d.carrello=:carrello ORDER BY d.film.prezzo ASC")
    Page<DettaglioCarrello> findAllByOrderByQuantitaAsc(@Param("carrello") Carrello carrello, Pageable pageable);

    @Query("SELECT d FROM DettaglioCarrello d WHERE d.carrello=:carrello ORDER BY d.film.prezzo DESC")
    Page<DettaglioCarrello> findAllByOrderByQuantitaDesc(@Param("carrello") Carrello carrello, Pageable pageable);

}