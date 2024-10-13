package org.example.movietime.services;

import org.example.movietime.dto.ClienteDTO;
import org.example.movietime.dto.MetodoPagamentoDTO;
import org.example.movietime.mapper.ClienteMapper;
import org.example.movietime.mapper.MetodoPagamentoMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.example.movietime.repositories.ClienteRepository;
import org.example.movietime.exceptions.ClienteNotFoundException;

import java.util.List;

@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;
    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Transactional(readOnly = true)
    public ClienteDTO getProfile(int idCliente) throws ClienteNotFoundException {
        return ClienteMapper.toDTO(clienteRepository.findById(idCliente).orElseThrow(ClienteNotFoundException::new));
    }

    @Transactional(readOnly = true)
    public List<MetodoPagamentoDTO> getMetodiPagamento(int idCliente) throws ClienteNotFoundException {
        return MetodoPagamentoMapper.toDTOList(
                clienteRepository.findById(idCliente)
                .orElseThrow(ClienteNotFoundException::new)
                .getMetodiPagamento()
        );
    }

}
