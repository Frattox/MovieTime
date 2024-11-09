package org.example.movietime.services;


//La classe servirà per gestire le operazioni per il carrello

import org.example.movietime.dto.DettaglioCarrelloDTO;
import org.example.movietime.entities.Carrello;
import org.example.movietime.entities.DettaglioCarrello;
import org.example.movietime.entities.Film;
import org.example.movietime.exceptions.*;
import org.example.movietime.mapper.DettaglioCarrelloMapper;
import org.example.movietime.mapper.FilmMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.example.movietime.repositories.CarrelloRepository;
import org.example.movietime.repositories.ClienteRepository;
import org.example.movietime.repositories.DettaglioCarrelloRepository;
import org.example.movietime.repositories.FilmRepository;
import org.example.movietime.util.Utils;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarrelloService {
    private final ClienteRepository clienteRepository;
    private final DettaglioCarrelloRepository dettaglioCarrelloRepository;
    private final FilmRepository filmRepository;
    private final CarrelloRepository carrelloRepository;
    private final int PAGE_SIZE = 20;
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
            throws FilmWornOutException, FilmNotFoundException, ClienteNotFoundException, CarrelloNotFoundException {
        //verifica che la quantità sia positiva
        if(quantity <= 0) throw new InvalidParameterException();

        //prendo il film dalla repository
        Film film = filmRepository.findByTitoloAndFormato(titolo, formato).orElseThrow(FilmNotFoundException::new);

        //verifica della disponibilità del film
        if(!Utils.isQuantityOk(film,quantity))throw new FilmWornOutException();

        //reperisco il carrello associato al cliente
        Carrello carrello = carrelloRepository.findByIdCliente(idCliente).orElseThrow(CarrelloNotFoundException::new);

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
    @Transactional(readOnly = true)
    public DettaglioCarrelloDTO getSingleDettaglioCarrello(int idDettaglioCarrello, int idCliente)
            throws FilmNotFoundException, CarrelloNotFoundException {
        Carrello carrello = carrelloRepository.findByIdCliente(idCliente).orElseThrow(CarrelloNotFoundException::new);
        Optional<DettaglioCarrello> optionalDettaglioCarrello = dettaglioCarrelloRepository.findByIdDettaglioCarrelloAndCarrello(idDettaglioCarrello,carrello);
        return optionalDettaglioCarrello
                .map(DettaglioCarrelloMapper::toDTO)
                .orElseThrow(FilmNotFoundException::new);
    }

    private Carrello findByIdCliente(int idCliente) throws CarrelloNotFoundException {
        return carrelloRepository.findByIdCliente(idCliente).orElseThrow(CarrelloNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public List<DettaglioCarrelloDTO> getDettagliCarrelloOrdinati(int idCliente, int pageNumber, String sortBy, String order) throws CarrelloNotFoundException {
        Pageable pageable = PageRequest.of(pageNumber, PAGE_SIZE);
        Page<DettaglioCarrello> page;

        Carrello carrello = findByIdCliente(idCliente);

        switch (sortBy.toLowerCase()) {
            case "titolo":
                page = order.equalsIgnoreCase("asc")
                        ? dettaglioCarrelloRepository.findAllOrderByTitoloAsc(carrello, pageable)
                        : dettaglioCarrelloRepository.findAllOrderByTitoloDesc(carrello, pageable);
                break;
            case "prezzo":
                page = order.equalsIgnoreCase("asc")
                        ? dettaglioCarrelloRepository.findAllOrderByPrezzoAsc(carrello, pageable)
                        : dettaglioCarrelloRepository.findAllOrderByPrezzoDesc(carrello, pageable);
                break;
            case "quantita":
                page = order.equalsIgnoreCase("asc")
                        ? dettaglioCarrelloRepository.findAllByOrderByQuantitaAsc(carrello, pageable)
                        : dettaglioCarrelloRepository.findAllByOrderByQuantitaDesc(carrello, pageable);
                break;
            default:
                return new ArrayList<>(); // Ritorna una lista vuota se sortBy non è valido
        }

        if (page.isEmpty())
            return new ArrayList<>();

        return page.getContent().stream().map(DettaglioCarrelloMapper::toDTO).collect(Collectors.toList());
    }




}
