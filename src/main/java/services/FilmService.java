package services;

import dto.FilmDTO;
import entities.Film;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import mapper.FilmMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repositories.FilmRepository;
import resources.exceptions.FilmNotFoundException;
import resources.exceptions.FilmWornOutException;
import util.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FilmService {

    @PersistenceContext
    private EntityManager entityManager;

    private final FilmRepository filmRepository;

    public FilmService(FilmRepository filmRepository){
        this.filmRepository = filmRepository;
    }

    //per i prodotti principali presenti in home
    @Transactional(readOnly = true)
    public List<FilmDTO> getAllFilms(int pageNumber){
        Pageable pageable = PageRequest.of(pageNumber,30);
        Page<Film> page = filmRepository.findAll(pageable);
        if(page.isEmpty())
            return new ArrayList<>();
        return page.getContent().stream().map(FilmMapper::toDTO).collect(Collectors.toList());
    }

    //per ritornare il film selezionato, magari per ottenere dettagli
    @Transactional(readOnly = true)
    public FilmDTO getFilm(int id){
        if(id<0)
            throw new IllegalArgumentException("Id film non idoneo");
        Optional<Film> optionalFilm = filmRepository.findByIdFilm(id);
        return optionalFilm.map(FilmMapper::toDTO).orElse(null);
    }

    //per la ricerca di film
    @Transactional(readOnly = true)
    public List<FilmDTO> getAllFilmsLike(String titolo, int pageNumber){
        Pageable pageable = PageRequest.of(pageNumber,30);
        Page<Film> page = filmRepository.findAllByTitolo(titolo, pageable);
        if(page.isEmpty())
            return new ArrayList<>();
        return page.getContent().stream().map(FilmMapper::toDTO).collect(Collectors.toList());
    }

    //metodo per il decremento di q pezzi acquistati
    @Transactional(readOnly = false)
    public void decrQuantity(int id, int q) throws FilmWornOutException, FilmNotFoundException {
        Optional<Film> optionalFilm = filmRepository.findByIdFilm(id);
        if(optionalFilm.isEmpty()) throw new FilmNotFoundException();
        Film film = optionalFilm.get();
        if(!Utils.isQuantityOk(film,q)) throw new FilmWornOutException();
        film.setQuantita(film.getQuantita()-q);
        entityManager.merge(film);
    }


}
