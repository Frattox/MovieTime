package repositories;

import entities.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FilmRepository extends JpaRepository<Film, Integer> {


    boolean existsByIdFilm(Film film);

    Optional<Film> findByTitoloAndFormato(String titolo, String formato);




}
