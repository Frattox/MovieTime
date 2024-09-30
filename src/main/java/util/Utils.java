package util;

import entities.Film;
import resources.exceptions.FilmWornOutException;

public class Utils {
    public static boolean isQuantityOk(Film film, int q){
        int filmDisponibility = film.getQuantita();
        return filmDisponibility - q > 0;
    }
}
