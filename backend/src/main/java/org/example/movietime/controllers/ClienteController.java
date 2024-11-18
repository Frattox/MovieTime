package org.example.movietime.controllers;

import org.example.movietime.dto.MetodoPagamentoDTO;
import org.example.movietime.exceptions.ClienteNotFoundException;
import org.example.movietime.dto.ClienteDTO;
import org.example.movietime.services.ClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    private final ClienteService clienteService;


    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public ResponseEntity<ClienteDTO> getProfile(){
        try{
            Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String email = (String) jwt.getClaims().get("email");
            int idCliente = clienteService.getProfile(email).getIdCliente();
            ClienteDTO clienteDTO = clienteService.getProfile(idCliente);
            return ResponseEntity.ok(clienteDTO);
        }catch(ClienteNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    @PostMapping
    public ResponseEntity<String> addCliente(){
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = (String) jwt.getClaims().get("email");
        String nome = (String) jwt.getClaims().get("given_name");
        String cognome = (String) jwt.getClaims().get("family_name");

        try{
            clienteService.addCliente(email, nome, cognome);
            return ResponseEntity.ok("Cliente aggiunto con successo!");
        }catch(ClienteNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente non trovato");
        }
    }


    @GetMapping("/metodi-pagamento")
    public ResponseEntity<List<MetodoPagamentoDTO>> getMetodiPagamento(){
        try {
            Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String email = (String) jwt.getClaims().get("email");
            int idCliente = clienteService.getProfile(email).getIdCliente();
            List<MetodoPagamentoDTO> metodiPagamento = clienteService.getMetodiPagamento(idCliente);
            return ResponseEntity.ok(metodiPagamento);
        } catch (ClienteNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/metodi-pagamento")
    public ResponseEntity<String> addMetodoPagamento(
            @RequestParam("numero") int numero,
            @RequestParam("tipo") String tipo
    ){
        try {
            Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String email = (String) jwt.getClaims().get("email");
            int idCliente = clienteService.getProfile(email).getIdCliente();
            clienteService.addMetodoPagamento(idCliente, numero, tipo);
            return ResponseEntity.status(HttpStatus.CREATED).body("Metodo di pagamento aggiunto con successo.");
        } catch (ClienteNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente non trovato.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


}
