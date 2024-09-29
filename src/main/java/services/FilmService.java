package services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repositories.FilmRepository;

@Service
public class FilmService {

    private FilmRepository filmRepository;

    public FilmService(FilmRepository filmRepository){
        this.filmRepository = filmRepository;
    }

    @Transactional
    public void getAllFilms(){

    }

}
