package org.example.movietime.services;

import org.example.movietime.dto.CarrelloDTO;
import org.example.movietime.dto.OrdineDTO;
import org.example.movietime.exceptions.*;
import org.example.movietime.mapper.OrdineMapper;
import org.example.movietime.entities.*;
import org.example.movietime.repositories.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.example.movietime.util.Utils;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.*;

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
    public void acquistaDalCarrello(
            int idCliente,
            String indirizzo,
            int idMetodoDiPagamento,
            CarrelloDTO carrelloDTO
    )
            throws FilmWornOutException, ClienteNotFoundException, FilmNotFoundException, DettaglioCarrelloNotFoundException, CarrelloNotFoundException, MetodoDiPagamentoNotFoundException {

        List<Integer> idDettagliCarrello = carrelloDTO.getIdDettagliCarrello();
        List<Integer> quantita = carrelloDTO.getQuantita();
        List<Float> prezzi = carrelloDTO.getPrezzi();

        Cliente cliente = clienteRepository.findById(idCliente).orElseThrow(ClienteNotFoundException::new);

        Carrello carrello = carrelloRepository.findByIdCliente(idCliente)
                .orElseThrow(CarrelloNotFoundException::new);

        MetodoPagamento metodoPagamento = getMetodoPagamento(idMetodoDiPagamento, idMetodoDiPagamento);

        //la lista di id dei dettagli carrello me la invia il client e da lì verifico che non esistono incongruenze con il db
        List<DettaglioCarrello> dettagliCarrello = new LinkedList<>();
        for(int d: idDettagliCarrello){
            if(d<=0) throw new InvalidParameterException();
            DettaglioCarrello dettaglioCarrello = dettaglioCarrelloRepository
                    .findByIdAndClienteWithLock(d, idCliente)
                    .orElseThrow(DettaglioCarrelloNotFoundException::new);
            dettagliCarrello.add(dettaglioCarrello);
        }

        controllaDettagliCarrello(dettagliCarrello,quantita,prezzi);

        //creo il nuovo ordine
        Ordine ordine = new Ordine();
        ordine.setMetodoPagamento(metodoPagamento);
        ordine.setCarrello(carrello);
        ordine.setIndirizzo(indirizzo);
        ordine.setStato("Preparazione");
        ordine.setDataOrdine(LocalDateTime.now());

        aggiornaOrdine(ordine,dettagliCarrello, idDettagliCarrello);
    }

    private void controllaDettagliCarrello(
            List<DettaglioCarrello> dettagliCarrello,
            List<Integer> quantita,
            List<Float> prezzi) {
        //hanno la stessa grandezza
        if(dettagliCarrello.size()!=quantita.size() || dettagliCarrello.size()!=prezzi.size())
            throw new InvalidParameterException("size");

        for(int i=0; i<dettagliCarrello.size();i++){
            //corrispondono le quantita
            int q = dettagliCarrello.get(i).getQuantita();
            if(q!=quantita.get(i)) throw new InvalidParameterException("quantita");
            //corrispondono i prezzi
            float p = dettagliCarrello.get(i).getPrezzoUnita();
            if(p!=prezzi.get(i)) throw new InvalidParameterException("prezzo");
        }
    }

        @Transactional
    protected void aggiornaOrdine(
            Ordine ordine,
            List<DettaglioCarrello> dettagliCarrello,
            List<Integer> idDettagliCarrello
        ) throws FilmWornOutException, FilmNotFoundException, ClienteNotFoundException, CarrelloNotFoundException, MetodoDiPagamentoNotFoundException {
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

        dettaglioCarrelloRepository.deleteAllById(idDettagliCarrello);

        ordine.setCarrello(carrelloRepository.findById(ordine.getCarrello().getIdCarrello()).orElseThrow(CarrelloNotFoundException::new));
        ordine.setMetodoPagamento(metodoPagamentoRepository.findById(ordine.getMetodoPagamento().getIdMetodoPagamento()).orElseThrow(MetodoDiPagamentoNotFoundException::new));
        ordineRepository.save(ordine);

        //meglio salvare la lista dopo che sia stata recuperata interamente, senza eccezioni
        dettaglioOrdineRepository.saveAll(dettagliOrdine);

        for(Film f: films.keySet())
        {
            int newQuantity = films.get(f);
            f = filmRepository.findByIdWithLock(f.getIdFilm()).orElseThrow(FilmNotFoundException::new);
            f.setQuantita(newQuantity);
        }
        filmRepository.saveAll(films.keySet());
    }


    private MetodoPagamento getMetodoPagamento(int idMetodoDiPagamento, int idCliente) throws MetodoDiPagamentoNotFoundException, ClienteNotFoundException {
        Cliente cliente = clienteRepository.findById(idCliente).orElseThrow(ClienteNotFoundException::new);
        return metodoPagamentoRepository.findByIdMetodoPagamentoAndCliente(idMetodoDiPagamento,cliente).orElseThrow(MetodoDiPagamentoNotFoundException::new);}

    //cliccare un film e acquistare direttamente quel film, senza passare dal carrello
    @Transactional
    public void acquistaFilm(
            int idCliente,
            int idFilm,
            int quantity,
            String indirizzo,
            int idMetodoDiPagamento)
            throws FilmNotFoundException, FilmWornOutException, CarrelloNotFoundException, MetodoDiPagamentoNotFoundException, ClienteNotFoundException {

        //recupero il film
        Film film = filmRepository.findById(idFilm).orElseThrow(FilmNotFoundException::new);

        //verifico che sia disponibile la quantità richiesta
        if(Utils.isQuantityNotOk(film, quantity)) throw new FilmWornOutException();

        //recupero il metodo di pagamento
        MetodoPagamento metodoPagamento = getMetodoPagamento(idMetodoDiPagamento,idCliente);

        //recupero il carrello
        Carrello carrello = carrelloRepository.findByIdCliente(idCliente)
                .orElseThrow(CarrelloNotFoundException::new);

        //genero l'ordine
        Ordine ordine = new Ordine();
        ordine.setStato("Preparazione");
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

        film.setQuantita(film.getQuantita()-quantity);
        filmRepository.save(film);

        metodoPagamentoRepository.save(metodoPagamento);
        ordineRepository.save(ordine);
        dettaglioOrdineRepository.save(dettaglioOrdine);
    }

    //Dalla lista di ordini effettuati, si clicca uno in particolare
    @Transactional(readOnly = true)
    public OrdineDTO getSingleOrdine(
            int idOrdine,
            int idCliente
    ) throws OrdineNotFoundException, ClienteNotFoundException {
        clienteRepository.findById(idCliente).orElseThrow(ClienteNotFoundException::new);
        return OrdineMapper.toDTO(
                ordineRepository
                        .findByIdAndCliente(idOrdine,idCliente)
                        .orElseThrow(OrdineNotFoundException::new)
        );
    }

    @Transactional(readOnly = true)
    public List<OrdineDTO> getAllOrdiniPaged(
            int idCliente,
            int pageNumber,
            String order) throws ClienteNotFoundException {
        Page<Ordine> page;
        clienteRepository.findById(idCliente).orElseThrow(ClienteNotFoundException::new);
        Pageable pageable = PageRequest.of(pageNumber, 20);

        if ("asc".equalsIgnoreCase(order)) {
            page = ordineRepository.findAllOrderByDataAsc(idCliente, pageable);
        } else if ("desc".equalsIgnoreCase(order)) {
            page = ordineRepository.findAllOrderByDataDesc(idCliente, pageable);
        } else {
            page = ordineRepository.findAllByCliente(idCliente,pageable);
        }

        if (page.isEmpty()) {
            return new ArrayList<>();
        }
        return OrdineMapper.toDTOList(page.getContent());
    }


}
