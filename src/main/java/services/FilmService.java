package services;

import dto.FilmDTO;
import entities.Film;
import mapper.FilmMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repositories.FilmRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmService {

    private FilmRepository filmRepository;

    public FilmService(FilmRepository filmRepository){
        this.filmRepository = filmRepository;
    }

    //per la home
    @Transactional(readOnly = true)
    public List<FilmDTO> getAllFilms(int pageNumber){
        Pageable pageable = PageRequest.of(pageNumber,30);
        Page<Film> page = filmRepository.findAll(pageable);
        if(page.isEmpty())
            return new ArrayList<>();
        return page.getContent().stream().map(FilmMapper::toDTO).collect(Collectors.toList());
    }

}
