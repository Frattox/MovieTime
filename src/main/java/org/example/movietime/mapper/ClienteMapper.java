package org.example.movietime.mapper;

import org.example.movietime.dto.ClienteDTO;
import org.example.movietime.entities.Cliente;

public class ClienteMapper {

    // Conversione da ClienteDTO a Cliente (Entity)
    public static Cliente toEntity(ClienteDTO clienteDTO) {
        if (clienteDTO == null) {
            return null;
        }

        // Usando il costruttore con tutti i parametri per l'entità Cliente
        return new Cliente(
                clienteDTO.getIdCliente(),
                clienteDTO.getNome(),
                clienteDTO.getCognome(),
                clienteDTO.getUsername(),
                clienteDTO.getEmail(),
                clienteDTO.getPassword(),
                clienteDTO.getDataRegistrazione(),
                //todo: da vedere meglio come sistemare
                null, // carrello non è presente nel DTO
                null, // ordini non è presente nel DTO
                null  // metodiPagamento non è presente nel DTO
        );
    }

    // Conversione da Cliente (Entity) a ClienteDTO
    public static ClienteDTO toDTO(Cliente cliente) {
        if (cliente == null) {
            return null;
        }

        // Usando il costruttore con tutti i parametri per il DTO ClienteDTO
        return new ClienteDTO(
                cliente.getIdCliente(),
                cliente.getNome(),
                cliente.getCognome(),
                cliente.getUsername(),
                cliente.getEmail(),
                cliente.getPassword(),
                cliente.getDataRegistrazione()
        );
    }
}
