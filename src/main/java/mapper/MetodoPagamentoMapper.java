package mapper;

import dto.MetodoPagamentoDTO;
import entities.MetodoPagamento;
import entities.Cliente;
import java.util.Objects;

public class MetodoPagamentoMapper {

    public static MetodoPagamento toMetodoPagamento(MetodoPagamentoDTO dto) {
        if (dto == null) {
            return null;
        }
        return new MetodoPagamento(
                dto.getIdMetodoPagamento(),
                dto.getTipo(),
                dto.getNumero(),
                //todo: da gestire dto
                null
        );
    }

    public static MetodoPagamentoDTO toDTO(MetodoPagamento metodoPagamento) {
        if (metodoPagamento == null) {
            return null;
        }
        return new MetodoPagamentoDTO(
                metodoPagamento.getIdMetodoPagamento(),
                metodoPagamento.getTipo(),
                metodoPagamento.getNumero(),
                metodoPagamento.getMetodoPagamentoCliente() != null ? metodoPagamento.getMetodoPagamentoCliente().getIdCliente() : 0 // Recupera l'ID del cliente
        );
    }
}
