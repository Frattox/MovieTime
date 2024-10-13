package org.example.movietime.repositories;

import org.example.movietime.entities.Carrello;
import org.example.movietime.entities.DettaglioCarrello;
import org.example.movietime.entities.Film;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DettaglioCarrelloRepository extends JpaRepository<DettaglioCarrello,Integer> {

    @Query("DELETE FROM DettaglioCarrello d WHERE d IN ?1")
    void deleteAllByDettagliCarrello(List<DettaglioCarrello> dettagliCarrello);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT d FROM DettaglioCarrello d WHERE d.idDettaglioCarrello = :id AND d.carrello.cliente.idCliente = :idCliente")
    Optional<DettaglioCarrello> findByIdAndClienteWithLock(@Param("id") int idDettaglioCarrello, @Param("idCliente") int idCliente);

    Optional<DettaglioCarrello> findByFilmAndCarrello(Film film, Carrello carrello);

    Optional<DettaglioCarrello> findByIdDettaglioCarrelloAndCarrello(int idDettaglioCarrello, Carrello carrello);

    @Query("SELECT d FROM DettaglioCarrello d WHERE d.film.titolo LIKE %:titolo% AND d.carrello = :carrello")
    List<DettaglioCarrello> findByTitleLike(@Param("titolo") String titolo, @Param("carrello") Carrello carrello);

    @Query("SELECT d FROM DettaglioCarrello d WHERE d.carrello=:carrello ORDER BY d.film.titolo DESC")
    List<DettaglioCarrello> findAllOrderByTitoloDesc(@Param("carrello") Carrello carrello);

    @Query("SELECT d FROM DettaglioCarrello d WHERE d.carrello=:carrello ORDER BY d.film.titolo ASC")
    List<DettaglioCarrello> findAllOrderByTitoloAsc(@Param("carrello") Carrello carrello);

    @Query("SELECT d FROM DettaglioCarrello d WHERE d.carrello=:carrello ORDER BY d.film.prezzo DESC")
    List<DettaglioCarrello> findAllOrderByPrezzoDesc(@Param("carrello") Carrello carrello);

    @Query("SELECT d FROM DettaglioCarrello d WHERE d.carrello=:carrello ORDER BY d.film.prezzo ASC")
    List<DettaglioCarrello> findAllOrderByPrezzoAsc(@Param("carrello") Carrello carrello);

    @Query("SELECT d FROM DettaglioCarrello d WHERE d.carrello=:carrello ORDER BY d.film.prezzo ASC")
    List<DettaglioCarrello> findAllByOrderByQuantitaAsc(@Param("carrello") Carrello carrello);

    @Query("SELECT d FROM DettaglioCarrello d WHERE d.carrello=:carrello ORDER BY d.film.prezzo DESC")
    List<DettaglioCarrello> findAllByOrderByQuantitaDesc(@Param("carrello") Carrello carrello);

}