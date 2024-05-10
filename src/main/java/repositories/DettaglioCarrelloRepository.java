package repositories;

import entities.Carrello;
import entities.DettaglioCarrello;
import entities.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DettaglioCarrelloRepository extends JpaRepository<DettaglioCarrello,Integer> {


    DettaglioCarrello findByFilmAndCarrello(Film film, Carrello carrello);

    //Lista di prodotti nel carrello che abbiano un prezzo minore di tot
    List<DettaglioCarrello> findByPrezzoUnitaLessThan(float prezzo);

    //Lista di prodotti nel carrello che abbiano un prezzo minore di tot
    List<DettaglioCarrello> findByPrezzoUnitaGreaterThan(float prezzo);
}
