package org.example.movietime.mapper;

import org.example.movietime.dto.MetodoPagamentoDTO;
import org.example.movietime.entities.MetodoPagamento;

import java.util.List;
import java.util.stream.Collectors;

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
                metodoPagamento.getCliente() != null ? metodoPagamento.getCliente().getIdCliente() : 0 // Recupera l'ID del cliente
        );
    }

    public static List<MetodoPagamentoDTO> toDTOList(List<MetodoPagamento> metodiPagamento) {
        if (metodiPagamento == null) {
            return null;
        }
        return metodiPagamento.stream()
                .map(MetodoPagamentoMapper::toDTO) // Usa il metodo toDTO per convertire ogni metodo di pagamento
                .collect(Collectors.toList());
    }
}
