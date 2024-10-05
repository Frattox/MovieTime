package mapper;

import dto.DettaglioCarrelloDTO;
import entities.Carrello;
import entities.DettaglioCarrello;

import java.util.List;
import java.util.stream.Collectors;

public class DettaglioCarrelloMapper {

    public static DettaglioCarrello toDettaglioCarrello(DettaglioCarrelloDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Il DTO non può essere null");
        }

        // Utilizza il costruttore con parametri per restituire un nuovo oggetto DettaglioCarrello
        return new DettaglioCarrello(
                dto.getIdDettaglioCarrello(),
                dto.getQuantita(),
                dto.getPrezzoUnita(),
                //todo: da mettere i rispettivi dto
                null,
                null // Qui impostiamo il film come null, da gestire in un altro punto
        );
    }

    public static DettaglioCarrelloDTO toDTO(DettaglioCarrello dettaglioCarrello) {
        if (dettaglioCarrello == null) {
            throw new IllegalArgumentException("Il dettaglio carrello non può essere null");
        }

        // Ritorna un nuovo oggetto DettaglioCarrelloDTO usando il costruttore con parametri
        return new DettaglioCarrelloDTO(
                dettaglioCarrello.getIdDettaglioCarrello(),
                dettaglioCarrello.getQuantita(),
                dettaglioCarrello.getPrezzoUnita(),
                dettaglioCarrello.getCarrello() != null ? dettaglioCarrello.getCarrello().getIdCarrello():0, // Recupera l'ID del film se esiste
                dettaglioCarrello.getFilm() != null ? dettaglioCarrello.getFilm().getIdFilm() : 0 // Recupera l'ID del film se esiste
        );
    }

    public static List<DettaglioCarrelloDTO> toDTOList(List<DettaglioCarrello> dettagliCarrello) {
        if (dettagliCarrello == null) {
            return null;
        }
        return dettagliCarrello.stream()
                .map(DettaglioCarrelloMapper::toDTO) // Usa il metodo toDTO per convertire ogni dettaglioCarrello
                .collect(Collectors.toList());
    }
}
