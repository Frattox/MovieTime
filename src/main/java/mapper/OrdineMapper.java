package mapper;

import dto.DettaglioOrdineDTO;
import dto.OrdineDTO;
import entities.Ordine;
import entities.Cliente;
import entities.Carrello;
import entities.MetodoPagamento;
import entities.DettaglioOrdine;
import java.util.List;
import java.util.Objects;
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
                .collect(Collectors.toList());

        return new OrdineDTO(
                ordine.getIdOrdine(),
                ordine.getDataOrdine(),
                ordine.getStato(),
                ordine.getIndirizzo(),
                ordine.getOrdineCliente() != null ? ordine.getOrdineCliente().getIdCliente() : 0,
                ordine.getOrdineCarrello() != null ? ordine.getOrdineCarrello().getIdCarrello() : 0,
                ordine.getMetodoPagamento() != null ? ordine.getMetodoPagamento().getIdMetodoPagamento() : 0
        );
    }
}
