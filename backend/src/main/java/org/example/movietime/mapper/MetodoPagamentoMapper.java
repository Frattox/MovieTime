package org.example.movietime.mapper;

import org.example.movietime.dto.MetodoPagamentoDTO;
import org.example.movietime.entities.Cliente;
import org.example.movietime.entities.MetodoPagamento;
import org.example.movietime.repositories.ClienteRepository;

import java.util.List;
import java.util.stream.Collectors;

public class MetodoPagamentoMapper {

    public static MetodoPagamento toMetodoPagamento(MetodoPagamentoDTO dto, Cliente cliente) {
        if (dto == null) {
            return null;
        }
        MetodoPagamento metodoPagamento = new MetodoPagamento();
        metodoPagamento.setNumero(dto.getNumero());
        metodoPagamento.setTipo(dto.getTipo());
        metodoPagamento.setCliente(cliente);
        return metodoPagamento;
    }

    public static MetodoPagamentoDTO toDTO(MetodoPagamento metodoPagamento) {
        if (metodoPagamento == null) {
            return null;
        }
        return new MetodoPagamentoDTO(
                metodoPagamento.getTipo(),
                metodoPagamento.getNumero(),
                metodoPagamento.getCliente() != null ? metodoPagamento.getCliente().getIdCliente() : 0
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
