package services;


//La classe servirà per gestire le operazioni per il carrello

import entities.Carrello;
import entities.DettaglioCarrello;
import entities.Film;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositories.DettaglioCarrelloRepository;
import repositories.FilmRepository;
import resources.exceptions.FilmWornOutException;

import java.security.InvalidParameterException;
import java.util.LinkedList;
import java.util.List;

@Service
public class CartService {

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private DettaglioCarrelloRepository dettaglioCarrelloRepository;

    @Autowired
    private EntityManager entityManager;

    public void addInCart(Carrello carrello, Film film, int quantity)
            throws FilmWornOutException, InvalidParameterException
    {
        //verifica che la quantità sia positiva
        if(quantity <= 0) throw new InvalidParameterException();

        //verifica della disponibilità del film
        int newQuantity = film.getQuantita() - quantity;
        if(newQuantity <= 0) throw new FilmWornOutException();

        //aggiunta del film al carrello
        //Caso 1. controllo se il film era già presente nel carrello, in tal caso aumento la sua quantità
        DettaglioCarrello dettaglioInCart = dettaglioCarrelloRepository.findByFilmAndCarrello(film, carrello);
        if(dettaglioInCart!=null)
        {
            int previousQuantity = dettaglioInCart.getQuantita();
            dettaglioInCart.setQuantita(previousQuantity+quantity);
        }

        //Caso 2. se il film non era già presente, allora bisogna aggiungerlo per la prima volta
        else
        {
            dettaglioInCart = new DettaglioCarrello();
            dettaglioInCart.setFilm(film);
            dettaglioInCart.setQuantita(quantity);
            dettaglioInCart.setCarrello(carrello);
            //DOMANDA: va gestito in caso di cambio di prezzo??? con la versione????
            dettaglioInCart.setPrezzoUnita(film.getPrezzo());
        }

        //in ogni caso, l'oggetto dettaglioInCart preso dalla tabella DettaglioCarrello, viene ricaricato con i
        // dati modificati nel DB
        dettaglioCarrelloRepository.save(dettaglioInCart);

        //per quanto riguarda la modifica della disponibilità nel film, questo spetta al repository dell'ordine
    }

    public List<DettaglioCarrello> getAllDettagliCarrello(){return dettaglioCarrelloRepository.findAll();}

    //in caso di restituire tutti e due i formati,è meglio restituire una lista con max due elementi
    public List<DettaglioCarrello> getSingleDettaglioCarrello(String titolo, Carrello carrello)
    {
        List<DettaglioCarrello> ret = new LinkedList<>();
        //cerco il film nei dettagli carrello
        for(DettaglioCarrello dc: carrello.getDettagliCarrello())
        {
            if(titolo.equals(dc.getFilm().getTitolo())) ret.add(dc);

            //sono stati trovati entrambi i formati
            if(ret.size()>1) break;
        }
        return ret;
    }

}
