package org.example.movietime.controllers;

import org.example.movietime.dto.DettaglioCarrelloDTO;
import org.example.movietime.exceptions.*;
import org.example.movietime.services.CarrelloService;
import org.example.movietime.services.ClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidParameterException;
import java.util.List;

@RestController
@RequestMapping("/carrello")
public class CarrelloController {

    private final CarrelloService carrelloService;
    private final ClienteService clienteService;

    public CarrelloController(CarrelloService carrelloService, ClienteService clienteService){
        this.carrelloService=carrelloService;
        this.clienteService = clienteService;
    }

    @GetMapping("/dettagli-carrello")
    public ResponseEntity<List<DettaglioCarrelloDTO>> getDettagliCarrelloOrdinati(
            @RequestParam(name = "p", defaultValue = "0") int pageNumber,
            @RequestParam String sortBy,
            @RequestParam String order) {
        List<DettaglioCarrelloDTO> dettagliCarrello;
        try {
            Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String email = (String) jwt.getClaims().get("email");
            int idCliente = clienteService.getProfile(email).getIdCliente();
            dettagliCarrello = carrelloService.getDettagliCarrelloOrdinati(idCliente, pageNumber, sortBy, order);
            System.out.println(dettagliCarrello.toString());
        } catch (CarrelloNotFoundException | ClienteNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(dettagliCarrello);
    }

    @GetMapping("/dettaglio")
    public ResponseEntity<DettaglioCarrelloDTO> getDettaglioCarrello(
            @RequestParam int idDettaglioCarrello) {
        try {
            Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String email = (String) jwt.getClaims().get("email");
            int idCliente = clienteService.getProfile(email).getIdCliente();
            DettaglioCarrelloDTO dettaglioCarrelloDTO = carrelloService.getSingleDettaglioCarrello(idDettaglioCarrello,idCliente);
            return ResponseEntity.ok(dettaglioCarrelloDTO);
        } catch (FilmNotFoundException | CarrelloNotFoundException | ClienteNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/aggiungi")
    public ResponseEntity<String> aggiungiAlCarrello(
            @RequestParam int idFilm,
            @RequestParam int quantity) {
        try {
            Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String email = (String) jwt.getClaims().get("email");
            int idCliente = clienteService.getProfile(email).getIdCliente();
            carrelloService.aggiungiAlCarrello(idCliente, idFilm, quantity);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (FilmWornOutException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Film non disponibile in quantità richiesta");
        } catch (FilmNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Film non trovato");
        } catch (ClienteNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente non trovato");
        } catch (CarrelloNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Carrello non trovato");
        } catch (InvalidParameterException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Quantità non valida");
        }
    }

    @PutMapping("/aggiorna")
    public ResponseEntity<String> aggiornaIlCarrello(
            @RequestParam int idDettaglioCarrello,
            @RequestParam int quantity,
            @RequestParam(defaultValue = "false") boolean dec) {
        try {
            Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String email = (String) jwt.getClaims().get("email");
            int idCliente = clienteService.getProfile(email).getIdCliente();
            carrelloService.aggiornaIlCarrello(idDettaglioCarrello, idCliente, quantity, dec);
            return ResponseEntity.ok("Quantità aggiornata con successo");
        } catch (DettaglioCarrelloNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Dettaglio carrello non trovato");
        } catch (FilmNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Film non trovato");
        } catch (FilmWornOutException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Film non disponibile in quantità richiesta");
        } catch (InvalidParameterException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Quantità non valida");
        } catch (ClienteNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utente non trovato");
        }
    }



}
