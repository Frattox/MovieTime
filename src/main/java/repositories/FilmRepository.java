package repositories;

import entities.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilmRepository extends JpaRepository<Film, Integer> {

    Film findByIdFilm(int id);

    //credo sia più utile Titolo+Formato
    Film findFilmByTitolo(String titolo);

    Film findByTitoloAndFormato(String titolo, String formato);

    List<Film> findByGenere(String genere);

    List<Film> findByAnnoUscita(int anno);

    List<Film> findByFormato(String formato);

    List<Film> findByPrezzoBefore(int max);

    //per find by regista forse sarebbe meglio utilizzare direttamente la lista memorizzata dalla classe regista





}
