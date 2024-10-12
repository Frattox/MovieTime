package services;

import dto.ClienteDTO;
import dto.MetodoPagamentoDTO;
import mapper.ClienteMapper;
import mapper.MetodoPagamentoMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repositories.ClienteRepository;
import resources.exceptions.ClienteNotFoundException;

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
