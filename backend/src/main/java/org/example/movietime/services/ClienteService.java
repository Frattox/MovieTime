package org.example.movietime.services;

import org.example.movietime.dto.ClienteDTO;
import org.example.movietime.dto.MetodoPagamentoDTO;
import org.example.movietime.entities.Cliente;
import org.example.movietime.entities.MetodoPagamento;
import org.example.movietime.mapper.ClienteMapper;
import org.example.movietime.mapper.MetodoPagamentoMapper;
import org.example.movietime.repositories.MetodoPagamentoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.example.movietime.repositories.ClienteRepository;
import org.example.movietime.exceptions.ClienteNotFoundException;

import java.util.List;

@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;
    private final MetodoPagamentoRepository metodoPagamentoRepository;
    public ClienteService(ClienteRepository clienteRepository, MetodoPagamentoRepository metodoPagamentoRepository) {
        this.clienteRepository = clienteRepository;
        this.metodoPagamentoRepository = metodoPagamentoRepository;
    }

    @Transactional(readOnly = true)
    public ClienteDTO getProfile(int idCliente) throws ClienteNotFoundException {
        return ClienteMapper.toDTO(clienteRepository.findById(idCliente).orElseThrow(ClienteNotFoundException::new));
    }

    @Transactional
    public void addMetodoPagamento(
            int idCliente,
            int numero,
            String tipo) throws ClienteNotFoundException {
        if(idCliente<=0 || numero <= 0 || tipo==null || tipo.isEmpty())
            throw new IllegalArgumentException("Dati carta non validi.");
        Cliente cliente = clienteRepository.findById(idCliente).orElseThrow(ClienteNotFoundException::new);
        if(!metodoPagamentoRepository.existsByNumeroAndCliente(numero,cliente)) {
            MetodoPagamento metodoPagamento = new MetodoPagamento();
            metodoPagamento.setCliente(cliente);
            metodoPagamento.setTipo(tipo);
            metodoPagamento.setNumero(numero);
            metodoPagamentoRepository.save(metodoPagamento);
        }
    }

    @Transactional(readOnly = true)
    public List<MetodoPagamentoDTO> getMetodiPagamento(int idCliente) throws ClienteNotFoundException {
        Cliente cliente = clienteRepository.findById(idCliente).orElseThrow(ClienteNotFoundException::new);
        return MetodoPagamentoMapper.toDTOList(
                metodoPagamentoRepository.findAllByCliente(cliente)
        );
    }



}
