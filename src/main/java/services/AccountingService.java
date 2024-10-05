package services;

import entities.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositories.ClienteRepository;

import java.util.List;

@Service
public class AccountingService {

    @Autowired
    private ClienteRepository clienteRepository;

    //La registrazione del cliente si farà successivamente con keycloak

    //@Transactional(readOnly = true)
    public List<Cliente> getAllClienti(){return clienteRepository.findAll();}


}
