package org.example.movietime.util;

import org.example.movietime.entities.Film;

public class Utils {
    public static boolean isQuantityOk(Film film, int q) {
        return film.getQuantita() - q > 0;
    }
}
