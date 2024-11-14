package org.example.movietime.mapper;

import org.example.movietime.exceptionHandler.dto.ClienteDTO;
import org.example.movietime.entities.Cliente;

public class ClienteMapper {

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
                cliente.getEmail(),
                cliente.getDataRegistrazione()
        );
    }
}
