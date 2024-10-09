package services;

import org.springframework.stereotype.Service;
import repositories.ClienteRepository;

@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;
    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }





}
