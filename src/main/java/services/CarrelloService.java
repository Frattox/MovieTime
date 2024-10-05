package services;


//La classe servirà per gestire le operazioni per il carrello

import dto.DettaglioCarrelloDTO;
import entities.Carrello;
import entities.Cliente;
import entities.DettaglioCarrello;
import entities.Film;
import jakarta.persistence.EntityManager;
import mapper.DettaglioCarrelloMapper;
import mapper.FilmMapper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import repositories.ClienteRepository;
import repositories.DettaglioCarrelloRepository;
import repositories.FilmRepository;
import resources.exceptions.FilmNotFoundException;
import resources.exceptions.FilmWornOutException;
import util.Utils;

import javax.swing.text.html.Option;
import java.security.InvalidParameterException;
import java.util.List;
import java.util.Optional;

@Service
public class CarrelloService {
    private ClienteRepository clienteRepository;
    private DettaglioCarrelloRepository dettaglioCarrelloRepository;
    private FilmRepository filmRepository;
    private EntityManager entityManager;
    public CarrelloService(ClienteRepository clienteRepository,
                           DettaglioCarrelloRepository dettaglioCarrelloRepository,
                           FilmRepository filmRepository,
                           EntityManager entityManager) {
        this.clienteRepository = clienteRepository;
        this.dettaglioCarrelloRepository = dettaglioCarrelloRepository;
        this.filmRepository = filmRepository;
        this.entityManager = entityManager;
    }


    @Transactional
    public void aggiungiAlCarrello(String email, String titolo, String formato, int quantity)
            throws FilmWornOutException, FilmNotFoundException {
        //verifica che la quantità sia positiva
        if(quantity <= 0) throw new InvalidParameterException();

        //TODO: da modificare, preferisco solo con l'id, probabilmente più versatile
        //prendo il film dalla repository
        Optional<Film> optionalFilm = filmRepository.findByTitoloAndFormato(titolo, formato);

        if(optionalFilm.isEmpty())
            throw new FilmNotFoundException();
        Film film = optionalFilm.get();

        //verifica della disponibilità del film
        if(!Utils.isQuantityOk(film,quantity))throw new FilmWornOutException();
        int filmDisponibility = film.getQuantita();

        //reperisco il carrello associato al cliente
        Cliente cliente = clienteRepository.findByEmail(email);
        Carrello carrello = cliente.getCarrello();

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

    //modifica della quantità presente nel dettaglio carrello
    @Transactional
    public void aggiornaIlCarrello(Carrello carrello, DettaglioCarrello dettaglioCarrello, int quantity)
    {
        //verifica che la quantità sia positiva e che il film sia disponibile rispetto alla quantità fornita
        if(quantity <= 0 || dettaglioCarrello.getFilm().getIdFilm() - quantity < 0) throw new InvalidParameterException();

        //così non ho problemi di dati incongruenti nel DB, dopo aver controllato che il film
        //sia presente nel DB
        if(dettaglioCarrelloRepository.existsByIdDettaglioCarrelloAndCarrello(dettaglioCarrello, carrello))
            entityManager.refresh(dettaglioCarrello);
        else
            throw new InvalidParameterException();

        dettaglioCarrello.setQuantita(quantity);
        dettaglioCarrelloRepository.save(dettaglioCarrello);
    }


    //quando si clicca sul singolo dettaglio carrello
    @Transactional
    public void rimuoviDettaglioCarrello(int idDettaglioCarrello, int idCarrello)
    {
        Optional<DettaglioCarrello> optionalDettaglioCarrello = dettaglioCarrelloRepository.findByIdDettaglioCarrelloAndCarrello(idDettaglioCarrello,idCarrello);
        optionalDettaglioCarrello.ifPresent(dettaglioCarrello -> dettaglioCarrelloRepository.deleteById(dettaglioCarrello.getIdDettaglioCarrello()));
    }

    //todo: gestire i dto
    //selezioni più di un film da eliminare
    @Transactional
    public void rimuoviDettagliCarrello(List<DettaglioCarrello> dettagliCarrello, Carrello carrello){dettaglioCarrelloRepository.deleteAllByDettagliCarrello(dettagliCarrello);}

    @Transactional(readOnly = true)
    public List<DettaglioCarrelloDTO> getAllDettagliCarrello(){return DettaglioCarrelloMapper.toDTOList(dettaglioCarrelloRepository.findAll());}

    //quando si clicca sul singolo dettaglio carrello
    @Transactional(readOnly = true)
    public DettaglioCarrelloDTO getSingleDettaglioCarrello(int id) throws FilmNotFoundException {
        Optional<DettaglioCarrello> optionalDettaglioCarrello = dettaglioCarrelloRepository.findById(id);
        return optionalDettaglioCarrello
                .map(DettaglioCarrelloMapper::toDTO)
                .orElseThrow(FilmNotFoundException::new);
    }


    //quando si cerca il titolo (anche incompleto)
    @Transactional(readOnly = true)
    public List<DettaglioCarrello> searchDettaglioCarrello(String titolo, Carrello carrello){return dettaglioCarrelloRepository.findByTitleLike(titolo, carrello);}

    //tutti i dettagli carrello ordinati in senso DECRESCENTE per titolo
    @Transactional(readOnly = true)
    public List<DettaglioCarrello> getDettagliCarrelloOrderedByTitoloDesc(Carrello carrello){return dettaglioCarrelloRepository.findAllOrderByTitoloDesc(carrello);}

    //tutti i dettagli carrello ordinati in senso CRESCENTE per titolo
    @Transactional(readOnly = true)
    public List<DettaglioCarrello> getDettagliCarrelloOrderedByTitoloAsc(Carrello carrello){return dettaglioCarrelloRepository.findAllOrderByTitoloAsc(carrello);}

    //tutti i dettagli carrello ordinati in senso DECRESCENTE per prezzo
    @Transactional(readOnly = true)
    public List<DettaglioCarrello> getDettagliCarrelloOrderedByPrezzoDesc(Carrello carrello){return dettaglioCarrelloRepository.findAllOrderByPrezzoDesc(carrello);}

    //tutti i dettagli carrello ordinati in senso CRESCENTE per prezzo
    @Transactional(readOnly = true)
    public List<DettaglioCarrello> getDettagliCarrelloOrderedByPrezzoAsc(Carrello carrello){return dettaglioCarrelloRepository.findAllOrderByPrezzoAsc(carrello);}

    //tutti i dettagli carrello ordinati in senso CRESCENTE per quantita
    @Transactional(readOnly = true)
    public List<DettaglioCarrello> getDettagliCarrelloOrderedByQuantitaAsc(Carrello carrello){return dettaglioCarrelloRepository.findAllByOrderByQuantitaAsc(carrello);}

    //tutti i dettagli carrello ordinati in senso DECRESCENTE per prezzo
    @Transactional(readOnly = true)
    public List<DettaglioCarrello> getDettagliCarrelloOrderedByQuantitaDesc(Carrello carrello){return dettaglioCarrelloRepository.findAllByOrderByQuantitaDesc(carrello);}


}
