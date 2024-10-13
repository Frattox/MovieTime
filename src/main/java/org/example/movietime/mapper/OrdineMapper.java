package org.example.movietime.mapper;

import org.example.movietime.dto.DettaglioOrdineDTO;
import org.example.movietime.dto.OrdineDTO;
import org.example.movietime.entities.Ordine;

import java.util.List;
import java.util.stream.Collectors;

public class OrdineMapper {

    public static Ordine toOrdine(OrdineDTO dto) {
        if (dto == null) {
            return null;
        }
        return new Ordine(
                dto.getIdOrdine(),
                dto.getDataOrdine(),
                dto.getStato(),
                dto.getIndirizzo(),
                //todo: gestire i dto
                null,
                null,
                null,
                null
        );
    }

    public static OrdineDTO toDTO(Ordine ordine) {
        if (ordine == null) {
            return null;
        }

        List<DettaglioOrdineDTO> dettagliOrdiniDTO = ordine.getDettagliOrdini().stream()
                .map(DettaglioOrdineMapper::toDTO)
                .toList();

        return new OrdineDTO(
                ordine.getIdOrdine(),
                ordine.getDataOrdine(),
                ordine.getStato(),
                ordine.getIndirizzo(),
                ordine.getCliente() != null ? ordine.getCliente().getIdCliente() : 0,
                ordine.getCarrello() != null ? ordine.getCarrello().getIdCarrello() : 0,
                ordine.getMetodoPagamento() != null ? ordine.getMetodoPagamento().getIdMetodoPagamento() : 0
        );
    }

    public static List<OrdineDTO> toDTOList(List<Ordine> ordine) {
        if (ordine == null) {
            return null;
        }
        return ordine.stream()
                .map(OrdineMapper::toDTO) // Usa il metodo toDTO per convertire ogni dettaglioCarrello
                .collect(Collectors.toList());
    }

    public static List<Ordine> toOrdineList(List<OrdineDTO> ordineDTO) {
        if (ordineDTO == null) {
            return null;
        }
        return ordineDTO.stream()
                .map(OrdineMapper::toOrdine) // Usa il metodo toDettaglioCarrello per convertire ogni DTO
                .collect(Collectors.toList());
    }
}