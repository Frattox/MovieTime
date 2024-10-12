package services;


//La classe servirà per gestire le operazioni per il carrello

import dto.DettaglioCarrelloDTO;
import entities.Carrello;
import entities.DettaglioCarrello;
import entities.Film;
import jakarta.persistence.EntityManager;
import mapper.DettaglioCarrelloMapper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import repositories.CarrelloRepository;
import repositories.ClienteRepository;
import repositories.DettaglioCarrelloRepository;
import repositories.FilmRepository;
import resources.exceptions.ClienteNotFoundException;
import resources.exceptions.DettaglioCarrelloNotFoundException;
import resources.exceptions.FilmNotFoundException;
import resources.exceptions.FilmWornOutException;
import util.Utils;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Optional;

@Service
public class CarrelloService {
    private final ClienteRepository clienteRepository;
    private final DettaglioCarrelloRepository dettaglioCarrelloRepository;
    private final FilmRepository filmRepository;
    private final CarrelloRepository carrelloRepository;
    public CarrelloService(ClienteRepository clienteRepository,
                           DettaglioCarrelloRepository dettaglioCarrelloRepository,
                           FilmRepository filmRepository,
                           CarrelloRepository carrelloRepository) {
        this.clienteRepository = clienteRepository;
        this.dettaglioCarrelloRepository = dettaglioCarrelloRepository;
        this.filmRepository = filmRepository;
        this.carrelloRepository = carrelloRepository;
    }


    @Transactional
    public void aggiungiAlCarrello(int idCliente, String titolo, String formato, int quantity)
            throws FilmWornOutException, FilmNotFoundException, ClienteNotFoundException{
        //verifica che la quantità sia positiva
        if(quantity <= 0) throw new InvalidParameterException();

        //prendo il film dalla repository
        Film film = filmRepository.findByTitoloAndFormato(titolo, formato).orElseThrow(FilmNotFoundException::new);

        //verifica della disponibilità del film
        if(!Utils.isQuantityOk(film,quantity))throw new FilmWornOutException();

        //reperisco il carrello associato al cliente
        Carrello carrello = clienteRepository.findById(idCliente).orElseThrow(ClienteNotFoundException::new).getCarrello();

        Optional<DettaglioCarrello> optionalDettaglioCarrello = dettaglioCarrelloRepository.findByFilmAndCarrello(film, carrello);

        //in ogni caso, l'oggetto dettaglioInCart preso dalla tabella DettaglioCarrello, viene caricato/aggiornato con i dati modificati nel DB
        dettaglioCarrelloRepository.save(getDettaglioCarrelloAggiornato(optionalDettaglioCarrello,film,carrello,quantity));
    }

    private DettaglioCarrello getDettaglioCarrelloAggiornato(Optional<DettaglioCarrello> optionalDettaglioCarrello, Film film, Carrello carrello, int quantity) throws FilmWornOutException {
        DettaglioCarrello dettaglioCarrello;
        int filmDisponibility = film.getQuantita();

        //aggiunta del film al carrello
        //Caso 1. controllo se il film era già presente nel carrello, in tal caso aumento la sua quantità
        if(optionalDettaglioCarrello.isPresent())
        {
            //verifica della disponibilità del film, considerando la quantità già presente nel carrello
            dettaglioCarrello = optionalDettaglioCarrello.get();
            int previousQuantity = dettaglioCarrello.getQuantita();
            int newQuantity = previousQuantity+quantity;
            if(filmDisponibility - newQuantity < 0) throw new FilmWornOutException();
            dettaglioCarrello.setQuantita(newQuantity);
        }

        //Caso 2. se il film non era già presente, allora bisogna aggiungerlo per la prima volta
        else
        {
            dettaglioCarrello = new DettaglioCarrello();
            dettaglioCarrello.setFilm(film);
            dettaglioCarrello.setQuantita(quantity);
            dettaglioCarrello.setCarrello(carrello);
            //DOMANDA: va gestito in caso di cambio di prezzo??? con la versione????
            dettaglioCarrello.setPrezzoUnita(film.getPrezzo());
        }
        return dettaglioCarrello;
    }

    //modifica della quantità presente nel dettaglio carrello
    @Transactional
    public void aggiornaIlCarrello(int idDettaglioCarrello, int quantity) throws DettaglioCarrelloNotFoundException, FilmNotFoundException, FilmWornOutException {
        DettaglioCarrello dettaglioCarrello = dettaglioCarrelloRepository.findById(idDettaglioCarrello).orElseThrow(DettaglioCarrelloNotFoundException::new);
        Film film = filmRepository.findById(dettaglioCarrello.getFilm().getIdFilm()).orElseThrow(FilmNotFoundException::new);

        //verifica che la quantità sia positiva e che il film sia disponibile rispetto alla quantità fornita
        if(quantity <= 0) throw new InvalidParameterException();
        if(!Utils.isQuantityOk(film,quantity)) throw new FilmWornOutException();

        //setting della nuova quantità
        dettaglioCarrello.setQuantita(quantity);
        dettaglioCarrelloRepository.save(dettaglioCarrello);
    }

    //quando si clicca sul singolo dettaglio carrello
    @Transactional
    public void rimuoviDettaglioCarrello(int idDettaglioCarrello, int idCarrello)
    {
        Optional<DettaglioCarrello> optionalDettaglioCarrello = dettaglioCarrelloRepository.findByIdDettaglioCarrelloAndCarrello(idDettaglioCarrello,carrelloRepository.findById(idCarrello).orElseThrow());
        optionalDettaglioCarrello.ifPresent(dettaglioCarrello -> dettaglioCarrelloRepository.deleteById(dettaglioCarrello.getIdDettaglioCarrello()));
    }

    //selezioni più di un film da eliminare
    @Transactional
    public void rimuoviDettagliCarrello(List<DettaglioCarrelloDTO> dettagliCarrelloDTO, Carrello carrello){dettaglioCarrelloRepository.deleteAllByDettagliCarrello(DettaglioCarrelloMapper.toDettaglioCarrelloList(dettagliCarrelloDTO));}

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
    public List<DettaglioCarrelloDTO> searchDettaglioCarrello(String titolo, Carrello carrello){return DettaglioCarrelloMapper.toDTOList(dettaglioCarrelloRepository.findByTitleLike(titolo, carrello));}

    //tutti i dettagli carrello ordinati in senso DECRESCENTE per titolo
    @Transactional(readOnly = true)
    public List<DettaglioCarrelloDTO> getDettagliCarrelloOrderedByTitoloDesc(Carrello carrello){return DettaglioCarrelloMapper.toDTOList(dettaglioCarrelloRepository.findAllOrderByTitoloDesc(carrello));}

    //tutti i dettagli carrello ordinati in senso CRESCENTE per titolo
    @Transactional(readOnly = true)
    public List<DettaglioCarrelloDTO> getDettagliCarrelloOrderedByTitoloAsc(Carrello carrello){return DettaglioCarrelloMapper.toDTOList(dettaglioCarrelloRepository.findAllOrderByTitoloAsc(carrello));}

    //tutti i dettagli carrello ordinati in senso DECRESCENTE per prezzo
    @Transactional(readOnly = true)
    public List<DettaglioCarrelloDTO> getDettagliCarrelloOrderedByPrezzoDesc(Carrello carrello){return DettaglioCarrelloMapper.toDTOList(dettaglioCarrelloRepository.findAllOrderByPrezzoDesc(carrello));}

    //tutti i dettagli carrello ordinati in senso CRESCENTE per prezzo
    @Transactional(readOnly = true)
    public List<DettaglioCarrelloDTO> getDettagliCarrelloOrderedByPrezzoAsc(Carrello carrello){return DettaglioCarrelloMapper.toDTOList(dettaglioCarrelloRepository.findAllOrderByPrezzoAsc(carrello));}

    //tutti i dettagli carrello ordinati in senso CRESCENTE per quantita
    @Transactional(readOnly = true)
    public List<DettaglioCarrelloDTO> getDettagliCarrelloOrderedByQuantitaAsc(Carrello carrello){return DettaglioCarrelloMapper.toDTOList(dettaglioCarrelloRepository.findAllByOrderByQuantitaAsc(carrello));}

    //tutti i dettagli carrello ordinati in senso DECRESCENTE per prezzo
    @Transactional(readOnly = true)
    public List<DettaglioCarrelloDTO> getDettagliCarrelloOrderedByQuantitaDesc(Carrello carrello){return DettaglioCarrelloMapper.toDTOList(dettaglioCarrelloRepository.findAllByOrderByQuantitaDesc(carrello));}
}
