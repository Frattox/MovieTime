package services;

import dto.DettaglioOrdineDTO;
import dto.MetodoPagamentoDTO;
import dto.OrdineDTO;
import entities.*;
import mapper.DettaglioOrdineMapper;
import mapper.MetodoPagamentoMapper;
import mapper.OrdineMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repositories.*;
import resources.exceptions.ClienteNotFoundException;
import resources.exceptions.DettaglioOrdineNotFoundException;
import resources.exceptions.FilmWornOutException;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrdineService {

    private final OrdineRepository ordineRepository;
    private final DettaglioOrdineRepository dettaglioOrdineRepository;
    private final FilmRepository filmRepository;
    private final ClienteRepository clienteRepository;

    public OrdineService(OrdineRepository ordineRepository, DettaglioOrdineRepository dettaglioOrdineRepository, FilmRepository filmRepository, ClienteRepository clienteRepository) {
        this.ordineRepository = ordineRepository;
        this.dettaglioOrdineRepository = dettaglioOrdineRepository;
        this.filmRepository = filmRepository;
        this.clienteRepository = clienteRepository;
    }

    //Il momento in cui il cliente effettua l'acquisto dal carrello
    @Transactional
    public void acquistaDalCarrello(int idCliente, String indirizzo, MetodoPagamentoDTO metodoPagamentoDTO)
            throws FilmWornOutException, ClienteNotFoundException {
        Optional<Cliente> clienteOptional = clienteRepository.findById(idCliente);
        Cliente cliente = clienteOptional.orElseThrow(ClienteNotFoundException::new);
        Carrello carrello = cliente.getCarrello();
        Ordine ordine = new Ordine();
        ordine.setCliente(cliente);
        ordine.setMetodoPagamento(MetodoPagamentoMapper.toMetodoPagamento(metodoPagamentoDTO));
        ordine.setCarrello(carrello);
        ordine.setIndirizzo(indirizzo);
        ordine.setStato("Preparazione");
        ordine.setDataOrdine(LocalDateTime.now());

        aggiornaOrdine(ordine,carrello);
    }

    private void aggiornaOrdine(Ordine ordine, Carrello carrello) throws FilmWornOutException {
        List<DettaglioCarrello> dettagliCarrello = carrello.getDettagliCarrello();
        List<DettaglioOrdine> dettagliOrdine = new LinkedList<>();
        Map<Film,Integer> films = new HashMap<>();

        //prima prendo le informazioni del carrello e le genero come un nuovo ordine
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

            //DA EVITARE: casomai ad un certo punto parte l'eccezione per uno dei dettagli
            //dettaglioOrdineRepository.save(dettaglioOrdine);
        }

        //meglio salvare la lista dopo che sia stata recuperata interamente, senza eccezioni
        dettaglioOrdineRepository.saveAll(dettagliOrdine);

        for(Film f: films.keySet())
        {
            int newQuantity = films.get(f);
            f.setQuantita(newQuantity);
        }
        filmRepository.saveAll(films.keySet());

    }

    @Transactional(readOnly = true)
    public List<OrdineDTO> getAllOrdini(int pageNumber){
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
    public List<OrdineDTO> getOrdiniOrderedByDataAsc(int idCliente, int pageNumber){
        Page<Ordine> page = ordineRepository.findAllOrderByDataAsc(idCliente, PageRequest.of(pageNumber,20));
        if(page.isEmpty())
            return new ArrayList<>();
        return OrdineMapper.toDTOList(page.getContent());
    }

    @Transactional(readOnly = true)
    public List<OrdineDTO> getOrdiniOrderedByDataDesc(int idCliente, int pageNumber){
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
