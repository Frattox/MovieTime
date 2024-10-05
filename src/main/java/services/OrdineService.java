package services;

import entities.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import repositories.DettaglioOrdineRepository;
import repositories.FilmRepository;
import resources.exceptions.FilmWornOutException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class OrdineService {

    private DettaglioOrdineRepository dettaglioOrdineRepository;
    private FilmRepository filmRepository;

    public OrdineService(DettaglioOrdineRepository dettaglioOrdineRepository, FilmRepository filmRepository) {
        this.dettaglioOrdineRepository = dettaglioOrdineRepository;
        this.filmRepository = filmRepository;
    }

    //Il momento in cui il cliente effettua l'acquisto dal carrello
    @Transactional
    public void purchaseFromCart(Cliente cliente, String indirizzo, MetodoPagamento metodoPagamento)
            throws FilmWornOutException
    {
        Carrello carrello = cliente.getCarrello();
        Ordine ordine = new Ordine();
        ordine.setOrdineCliente(cliente);
        ordine.setMetodoPagamento(metodoPagamento);
        ordine.setOrdineCarrello(carrello);
        ordine.setIndirizzo(indirizzo);
        ordine.setStato("Preparazione");
        ordine.setDataOrdine(LocalDateTime.now());


        List<DettaglioCarrello> dettagliCarrello = carrello.getDettagliCarrello();
        List<DettaglioOrdine> dettagliOrdine = new LinkedList<>();
        Map<Film,Integer> films = new HashMap<>();

        //prima prendo le informazioni del carrello e le genero come un nuovo ordine
        for(DettaglioCarrello dettaglioCarrello: dettagliCarrello)
        {
            DettaglioOrdine dettaglioOrdine = new DettaglioOrdine();

            //verifico prima che esiste la disponibilità
            Film film = dettaglioCarrello.getFilm();
            int quantity = dettaglioCarrello.getQuantita();
            int newQuantity = film.getQuantita() - quantity;
            if(newQuantity <= 0) throw new FilmWornOutException();

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


}
