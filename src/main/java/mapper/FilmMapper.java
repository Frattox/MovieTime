package mapper;

import dto.FilmDTO;
import entities.Film;

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
                filmDTO.getVersione(),
                filmDTO.getRegista()
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
                film.getVersione(),
                film.getRegista()
        );
    }
}
