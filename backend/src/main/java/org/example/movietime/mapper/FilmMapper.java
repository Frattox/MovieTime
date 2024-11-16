package org.example.movietime.mapper;

import org.example.movietime.dto.FilmDTO;
import org.example.movietime.entities.Film;

public class FilmMapper {

    public static Film toFilm(FilmDTO filmDTO) {
        if (filmDTO == null) {
            return null;
        }
        return new Film(
                filmDTO.getIdFilm(),
                filmDTO.getTitolo(),
                filmDTO.getAnnoUscita(),
                filmDTO.getGenere(),
                filmDTO.getFormato(),
                filmDTO.getPrezzo(),
                filmDTO.getQuantita(),
                filmDTO.getImmagine(),
                RegistaMapper.toRegista(filmDTO.getRegista())
        );
    }

    public static FilmDTO toDTO(Film film) {
        if (film == null) {
            return null;
        }

        return new FilmDTO(
                film.getIdFilm(),
                film.getTitolo(),
                film.getAnnoUscita(),
                film.getGenere(),
                film.getFormato(),
                film.getPrezzo(),
                film.getQuantita(),
                film.getImmagine(),
                RegistaMapper.toRegistaDTO(film.getRegista())
        );
    }
}
