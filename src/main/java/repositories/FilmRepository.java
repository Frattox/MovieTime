package repositories;

import entities.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilmRepository extends JpaRepository<Film, Integer> {


    boolean existsByIdFilm(Film film);

    Film findByTitoloAndFormato(String titolo, String formato);




}
