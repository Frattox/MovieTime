package repositories;

import entities.Film;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FilmRepository extends JpaRepository<Film, Integer> {


    boolean existsByIdFilm(Film film);

    Optional<Film> findByTitoloAndFormato(String titolo, String formato);

    @Query("SELECT f FROM Film f WHERE f.titolo LIKE %:titolo%")
    Page<Film> findAllByTitolo(@Param("titolo") String titolo, Pageable pageable);

}
