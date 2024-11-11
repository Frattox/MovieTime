package org.example.movietime.controllers;

import org.example.movietime.exceptions.ClienteNotFoundException;
import org.example.movietime.dto.ClienteDTO;
import org.example.movietime.services.ClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    private final ClienteService clienteService;


    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public ResponseEntity<ClienteDTO> getProfile(@RequestParam("idCliente") int idCliente){
        try{
            ClienteDTO clienteDTO = clienteService.getProfile(idCliente);
            return ResponseEntity.ok(clienteDTO);
        }catch(ClienteNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
