package org.example.movietime.mapper;

import org.example.movietime.dto.DettaglioOrdineDTO;
import org.example.movietime.dto.OrdineDTO;
import org.example.movietime.entities.DettaglioOrdine;
import org.example.movietime.entities.Film;
import org.example.movietime.entities.Ordine;

import java.util.List;
import java.util.stream.Collectors;

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

    public static List<DettaglioOrdineDTO> toDTOList(List<DettaglioOrdine> dettagliOrdine) {
        if (dettagliOrdine == null) {
            return null;
        }
        return dettagliOrdine.stream()
                .map(DettaglioOrdineMapper::toDTO)
                .collect(Collectors.toList());
    }

    public static DettaglioOrdineDTO toDTO(DettaglioOrdine dettaglioOrdine) {
        if (dettaglioOrdine == null) {
            return null;
        }
        return new DettaglioOrdineDTO(
                dettaglioOrdine.getIdDettaglioOrdine(),
                dettaglioOrdine.getQuantita(),
                dettaglioOrdine.getPrezzoUnita(),
                dettaglioOrdine.getFilm().getIdFilm() > 0 ? dettaglioOrdine.getFilm().getIdFilm() : 0
        );
    }
}
