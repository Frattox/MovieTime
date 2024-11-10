package org.example.movietime.services;

import org.example.movietime.dto.DettaglioCarrelloDTO;
import org.example.movietime.dto.MetodoPagamentoDTO;
import org.example.movietime.dto.OrdineDTO;
import org.example.movietime.exceptionHandler.repositories.*;
import org.example.movietime.exceptions.*;
import org.example.movietime.mapper.MetodoPagamentoMapper;
import org.example.movietime.mapper.OrdineMapper;
import org.example.movietime.entities.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.example.movietime.util.Utils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrdineService {

    private final OrdineRepository ordineRepository;
    private final DettaglioOrdineRepository dettaglioOrdineRepository;
    private final DettaglioCarrelloRepository dettaglioCarrelloRepository;
    private final FilmRepository filmRepository;
    private final ClienteRepository clienteRepository;
    private final MetodoPagamentoRepository metodoPagamentoRepository;
    private final CarrelloRepository carrelloRepository;

    public OrdineService(OrdineRepository ordineRepository
            , DettaglioOrdineRepository dettaglioOrdineRepository, DettaglioCarrelloRepository dettaglioCarrelloRepository
            , FilmRepository filmRepository
            , ClienteRepository clienteRepository
            , MetodoPagamentoRepository metodoPagamentoRepository
            , CarrelloRepository carrelloRepository) {
        this.ordineRepository = ordineRepository;
        this.dettaglioOrdineRepository = dettaglioOrdineRepository;
        this.dettaglioCarrelloRepository = dettaglioCarrelloRepository;
        this.filmRepository = filmRepository;
        this.clienteRepository = clienteRepository;
        this.metodoPagamentoRepository = metodoPagamentoRepository;
        this.carrelloRepository = carrelloRepository;
    }

    //Il momento in cui il cliente effettua l'acquisto dal carrello
    @Transactional
    public void acquistaDalCarrello(int idCliente, String indirizzo, MetodoPagamentoDTO metodoPagamentoDTO, List<DettaglioCarrelloDTO> dettagliCarrelloDTO)
            throws FilmWornOutException, ClienteNotFoundException, FilmNotFoundException, DettaglioCarrelloNotFoundException, CarrelloNotFoundException {

        Cliente cliente = clienteRepository.findById(idCliente).orElseThrow(ClienteNotFoundException::new);

        //la lista di dettagli carrello me la invia il client e da lì verifico che non esistono incongruenze con il db
        //così evito la race condition, prendendo il lock pessimistico
        List<DettaglioCarrello> dettagliCarrello = new LinkedList<>();
        for(DettaglioCarrelloDTO d: dettagliCarrelloDTO){
            DettaglioCarrello dettaglioCarrello = dettaglioCarrelloRepository
                    .findByIdAndClienteWithLock(d.getIdDettaglioCarrello(), idCliente)
                    .orElseThrow(DettaglioCarrelloNotFoundException::new);
            dettagliCarrello.add(dettaglioCarrello);
        }

        Carrello carrello = carrelloRepository.findByIdCliente(idCliente)
                .orElseThrow(CarrelloNotFoundException::new);

        //creo il nuovo ordine
        Ordine ordine = new Ordine();
        ordine.setCliente(cliente);
        MetodoPagamento metodoPagamento = getMetodoPagamento(metodoPagamentoDTO);
        ordine.setMetodoPagamento(metodoPagamento);
        ordine.setCarrello(carrello);
        ordine.setIndirizzo(indirizzo);
        ordine.setStato("Preparazione");
        ordine.setDataOrdine(LocalDateTime.now());

        aggiornaOrdine(ordine,carrello,dettagliCarrello);

        metodoPagamentoRepository.save(metodoPagamento);
    }

    @Transactional
    protected void aggiornaOrdine(Ordine ordine, Carrello carrello, List<DettaglioCarrello> dettagliCarrello) throws FilmWornOutException, FilmNotFoundException {
        List<DettaglioOrdine> dettagliOrdine = new LinkedList<>();
        Map<Film,Integer> films = new HashMap<>();

        //prima prendo le informazioni del carrello e genero come un nuovo ordine
        for(DettaglioCarrello dettaglioCarrello: dettagliCarrello)
        {
            DettaglioOrdine dettaglioOrdine = new DettaglioOrdine();

            //verifico prima che esiste la disponibilità
            Film film = dettaglioCarrello.getFilm();
            int newQuantity = film.getQuantita() - dettaglioCarrello.getQuantita();
            if(newQuantity<=0) throw new FilmWornOutException();

            films.put(film,newQuantity);

            //se è disponibile, effettuo l'acquisto
            dettaglioOrdine.setOrdine(ordine);
            dettaglioOrdine.setFilm(film);
            dettaglioOrdine.setQuantita(dettaglioCarrello.getQuantita());
            dettaglioOrdine.setPrezzoUnita(dettaglioCarrello.getPrezzoUnita());

            dettagliOrdine.add(dettaglioOrdine);
        }

        //meglio salvare la lista dopo che sia stata recuperata interamente, senza eccezioni
        dettaglioOrdineRepository.saveAll(dettagliOrdine);

        for(Film f: films.keySet())
        {
            int newQuantity = films.get(f);
            f = filmRepository.findByIdWithLock(f.getIdFilm()).orElseThrow(FilmNotFoundException::new);
            f.setQuantita(newQuantity);
        }

        filmRepository.saveAll(films.keySet());
        ordineRepository.save(ordine);
    }


    private MetodoPagamento getMetodoPagamento(MetodoPagamentoDTO metodoPagamentoDTO) {
        Optional<MetodoPagamento> metodoPagamentoOptional = metodoPagamentoRepository.findById(metodoPagamentoDTO.getIdMetodoPagamento());
        return metodoPagamentoOptional.orElseGet(
                () -> MetodoPagamentoMapper.toMetodoPagamento(metodoPagamentoDTO)
        );
    }

    //cliccare un film e acquistare direttamente quel film, senza passare dal carrello
    @Transactional
    public void acquistaFilm(int idCliente, int idFilm, int quantity,  String indirizzo, MetodoPagamentoDTO metodoPagamentoDTO) throws ClienteNotFoundException, FilmNotFoundException, FilmWornOutException, CarrelloNotFoundException {
        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(ClienteNotFoundException::new);

        //recupero il film
        Film film = filmRepository.findById(idFilm).orElseThrow(FilmNotFoundException::new);
        //verifico che sia disponibile la quantità richiesta
        if(Utils.isQuantityNotOk(film, quantity)) throw new FilmWornOutException();

        //recupero il metodo di pagamento
        MetodoPagamento metodoPagamento = getMetodoPagamento(metodoPagamentoDTO);

        //recupero il carrello
        Carrello carrello = carrelloRepository.findByIdCliente(idCliente)
                .orElseThrow(CarrelloNotFoundException::new);

        //genero l'ordine
        Ordine ordine = new Ordine();
        ordine.setStato("Preparazione");
        ordine.setCliente(cliente);
        ordine.setMetodoPagamento(metodoPagamento);
        ordine.setIndirizzo(indirizzo);
        ordine.setCarrello(carrello);
        ordine.setDataOrdine(LocalDateTime.now());

        //genero il dettaglio ordine
        DettaglioOrdine dettaglioOrdine = new DettaglioOrdine();
        dettaglioOrdine.setOrdine(ordine);
        dettaglioOrdine.setFilm(film);
        dettaglioOrdine.setQuantita(quantity);
        dettaglioOrdine.setPrezzoUnita(film.getPrezzo());

        metodoPagamentoRepository.save(metodoPagamento);
        ordineRepository.save(ordine);
        dettaglioOrdineRepository.save(dettaglioOrdine);
    }

    @Transactional(readOnly = true)
    public List<OrdineDTO> getAllOrdiniPaged(int pageNumber){
        Pageable pageable = PageRequest.of(pageNumber,20);
        Page<Ordine> page = ordineRepository.findAll(pageable);
        if(page.isEmpty())
            return new ArrayList<>();
        return page.getContent()
                .stream()
                .map(OrdineMapper::toDTO)
                .collect(Collectors.toList());
    }

    //Dalla lista di ordini effettuati, si clicca uno in particolare
    @Transactional(readOnly = true)
    public OrdineDTO getSingleOrdine(int idOrdine) throws OrdineNotFoundException {
        return OrdineMapper.toDTO(
                ordineRepository
                        .findById(idOrdine)
                        .orElseThrow(OrdineNotFoundException::new)
        );
    }

    @Transactional(readOnly = true)
    public List<OrdineDTO> getOrdiniOrderedByDataAscPaged(int idCliente, int pageNumber){
        Page<Ordine> page = ordineRepository.findAllOrderByDataAsc(idCliente, PageRequest.of(pageNumber,20));
        if(page.isEmpty())
            return new ArrayList<>();
        return OrdineMapper.toDTOList(page.getContent());
    }

    @Transactional(readOnly = true)
    public List<OrdineDTO> getOrdiniOrderedByDataDescPaged(int idCliente, int pageNumber){
        Page<Ordine> page = ordineRepository.findAllOrderByDataDesc(idCliente, PageRequest.of(pageNumber,20));
        if(page.isEmpty())
            return new ArrayList<>();
        return OrdineMapper.toDTOList(page.getContent());
    }

    @Transactional(readOnly = true)
    public List<OrdineDTO> getOrdiniBetween(int idCliente, LocalDateTime min, LocalDateTime max, int pageNumber) throws ClienteNotFoundException {
        Page<Ordine> page = ordineRepository
                .findByClienteAndDataOrdineBetween(
                        clienteRepository.findById(idCliente).orElseThrow(ClienteNotFoundException::new)
                        ,min
                        ,max
                        ,PageRequest.of(pageNumber,20)
                );
        if(page.isEmpty())
            return new ArrayList<>();
        return OrdineMapper.toDTOList(page.getContent());
    }
}
