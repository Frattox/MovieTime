package services;


//La classe servirà per gestire le operazioni per il carrello

import entities.Carrello;
import entities.DettaglioCarrello;
import entities.Film;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositories.DettaglioCarrelloRepository;
import repositories.DettaglioOrdineRepository;
import repositories.FilmRepository;
import resources.exceptions.FilmWornOutException;

import java.security.InvalidParameterException;
import java.util.LinkedList;
import java.util.List;

@Service
public class CartService {

    @Autowired
    private DettaglioCarrelloRepository dettaglioCarrelloRepository;

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private EntityManager entityManager;

    private Long lastVersion(Film film)
    {
        return filmRepository.findByIdFilm(film.getIdFilm()).getVersione();
    }

    @Transactional
    public void addInCart(Carrello carrello, Film film, int quantity)
            throws FilmWornOutException, InvalidParameterException
    {
        //verifica che la quantità sia positiva
        if(quantity <= 0) throw new InvalidParameterException();

        //così non ho problemi di dati incongruenti nel DB, dopo aver controllato che il film
        //sia presente nel DB
        if(filmRepository.existsByIdFilm(film.getIdFilm()))
            entityManager.refresh(film);
        else
            throw new InvalidParameterException();

        //controllo della versione (è giusto?)
//        long version = film.getVersione();
//        if(version != lastVersion(film))
//            entityManager.refresh(film);

        //verifica della disponibilità del film
        int filmDisponibility = film.getQuantita();
        if(filmDisponibility - quantity < 0) throw new FilmWornOutException();

        //aggiunta del film al carrello
        //Caso 1. controllo se il film era già presente nel carrello, in tal caso aumento la sua quantità
        DettaglioCarrello dettaglioInCart = dettaglioCarrelloRepository.findByFilmAndCarrello(film, carrello);
        if(dettaglioInCart!=null)
        {
            //verifica della disponibilità del film, considerando la quantità già presente nel carrello
            int previousQuantity = dettaglioInCart.getQuantita();
            int newQuantity = previousQuantity+quantity;
            if(filmDisponibility - newQuantity < 0) throw new FilmWornOutException();
            dettaglioInCart.setQuantita(newQuantity);
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

    @Transactional
    public List<DettaglioCarrello> getAllDettagliCarrello(){return dettaglioCarrelloRepository.findAll();}


    @Transactional
    public DettaglioCarrello getSingleDettaglioCarrello(Film film, Carrello carrello)
    {
        DettaglioCarrello ret = dettaglioCarrelloRepository.findByFilmAndCarrello(film,carrello);
        if(ret==null)
            throw new InvalidParameterException();
        return ret;
    }


    //in caso di restituire tutti e due i formati,è meglio restituire una lista con max due elementi
    @Transactional
    public List<DettaglioCarrello> getSingleDettaglioCarrello(String titolo, Carrello carrello)
    {
        List<DettaglioCarrello> ret = new LinkedList<>();
        //cerco il film nei dettagli carrello
        //NOTA: sarebbe meglio la query con "like", così in caso il titolo non l'ha scritto completo
        for(DettaglioCarrello dc: carrello.getDettagliCarrello())
        {
            if(titolo.equals(dc.getFilm().getTitolo())) ret.add(dc);

            //sono stati trovati entrambi i formati
            if(ret.size()>1) break;
        }
        return ret;
    }

}
