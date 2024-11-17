package org.example.movietime.services;

import org.example.movietime.dto.FilmDTO;
import org.example.movietime.entities.Film;
import org.example.movietime.mapper.FilmMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.example.movietime.repositories.FilmRepository;
import org.example.movietime.exceptions.FilmNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FilmService {

    private final FilmRepository filmRepository;
    private final int PAGE_SIZE = 20;

    public FilmService(FilmRepository filmRepository){
        this.filmRepository = filmRepository;
    }

    //per i prodotti principali presenti in home
    @Transactional(readOnly = true)
    public List<FilmDTO> getAllFilms(int pageNumber){
        Pageable pageable = PageRequest.of(pageNumber,PAGE_SIZE);
        Page<Film> page = filmRepository.findAll(pageable);
        if(page.isEmpty())
            return new ArrayList<>();
        return page.getContent().stream().map(FilmMapper::toDTO).collect(Collectors.toList());
    }

    //per ritornare il film selezionato, magari per ottenere dettagli
    @Transactional(readOnly = true)
    public FilmDTO getFilm(int id) throws FilmNotFoundException {
        Optional<Film> optionalFilm = filmRepository.findById(id);
        return optionalFilm
                .map(FilmMapper::toDTO)
                .orElseThrow(FilmNotFoundException::new);
    }

    //per la ricerca di film
    @Transactional(readOnly = true)
    public List<FilmDTO> getAllFilmsLike(String titolo, int pageNumber){
        Pageable pageable = PageRequest.of(pageNumber,PAGE_SIZE);
        Page<Film> page = filmRepository.findAllByTitolo(titolo, pageable);
        if(page.isEmpty())
            return new ArrayList<>();
        return page.getContent().stream().map(FilmMapper::toDTO).collect(Collectors.toList());
    }






}
