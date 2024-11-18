package org.example.movietime.services;

import org.example.movietime.dto.ClienteDTO;
import org.example.movietime.dto.MetodoPagamentoDTO;
import org.example.movietime.entities.Carrello;
import org.example.movietime.entities.Cliente;
import org.example.movietime.entities.MetodoPagamento;
import org.example.movietime.mapper.ClienteMapper;
import org.example.movietime.mapper.MetodoPagamentoMapper;
import org.example.movietime.repositories.CarrelloRepository;
import org.example.movietime.repositories.MetodoPagamentoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.example.movietime.repositories.ClienteRepository;
import org.example.movietime.exceptions.ClienteNotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;
    private final MetodoPagamentoRepository metodoPagamentoRepository;
    private final CarrelloRepository carrelloRepository;
    public ClienteService(ClienteRepository clienteRepository, MetodoPagamentoRepository metodoPagamentoRepository, CarrelloRepository carrelloRepository) {
        this.clienteRepository = clienteRepository;
        this.metodoPagamentoRepository = metodoPagamentoRepository;
        this.carrelloRepository = carrelloRepository;
    }

    @Transactional(readOnly = true)
    public ClienteDTO getProfile(int idCliente) throws ClienteNotFoundException {
        return ClienteMapper.toDTO(clienteRepository.findById(idCliente).orElseThrow(ClienteNotFoundException::new));
    }

    @Transactional(readOnly = true)
    public ClienteDTO getProfile(String email) throws ClienteNotFoundException {
        return ClienteMapper.toDTO(clienteRepository.findByEmail(email).orElseThrow(ClienteNotFoundException::new));
    }

    @Transactional
    public void addCliente(
            String email,
            String nome,
            String cognome
    ) throws ClienteNotFoundException {
        if(clienteRepository.existsByEmail(email))
            return;

        Cliente cliente = new Cliente();
        cliente.setNome(nome);
        cliente.setCognome(cognome);
        cliente.setEmail(email);
        cliente.setDataRegistrazione(LocalDate.now());
        clienteRepository.save(cliente);

        if(carrelloRepository.existsByCliente(cliente))
            return;

        Carrello carrello = new Carrello();
        carrello.setCliente(cliente);
        carrelloRepository.save(carrello);
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
