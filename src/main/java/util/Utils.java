package util;

import entities.Film;
import resources.exceptions.FilmWornOutException;

public class Utils {
    public static boolean isQuantityOk(Film film, int q) {
        return film.getQuantita() - q > 0;
    }
}
