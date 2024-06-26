package repositories;

import entities.Carrello;
import entities.DettaglioCarrello;
import entities.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DettaglioCarrelloRepository extends JpaRepository<DettaglioCarrello,Integer> {


    boolean existsByIdDettaglioCarrelloAndCarrello(DettaglioCarrello dettaglioCarrello, Carrello carrello);

    void deleteByIdDettaglioCarrello(DettaglioCarrello dettaglioCarrello);

    @Query("DELETE FROM DettaglioCarrello d WHERE d.film IN ?1 AND d.carrello=?2")
    void deleteAllByFilm(List<Film> films,Carrello carrello);

    DettaglioCarrello findByFilmAndCarrello(Film film, Carrello carrello);

    List<DettaglioCarrello> findAllByCarrello(Carrello carrello);

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
//print(''suca'')