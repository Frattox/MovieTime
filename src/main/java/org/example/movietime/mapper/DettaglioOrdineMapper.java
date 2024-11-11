package org.example.movietime.mapper;

import org.example.movietime.dto.DettaglioOrdineDTO;
import org.example.movietime.entities.DettaglioOrdine;
import org.example.movietime.entities.Film;

public class DettaglioOrdineMapper {

    public static DettaglioOrdine toDettaglioOrdine(DettaglioOrdineDTO dto, Film film) {
        if (dto == null) {
            return null;
        }
        return new DettaglioOrdine(
                dto.getIdDettaglioOrdine(),
                dto.getQuantita(),
                dto.getPrezzoUnita(),
                //todo: da mettere i rispettivi dto
                null,
                null
        );
    }

    public static DettaglioOrdineDTO toDTO(DettaglioOrdine dettaglioOrdine) {
        if (dettaglioOrdine == null) {
            return null;
        }
        return new DettaglioOrdineDTO(
                dettaglioOrdine.getIdDettaglioOrdine(),
                dettaglioOrdine.getQuantita(),
                dettaglioOrdine.getPrezzoUnita(),
                dettaglioOrdine.getFilm() != null ? dettaglioOrdine.getFilm().getIdFilm() : 0 // Recupera l'ID del film
        );
    }
}
