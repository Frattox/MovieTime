package org.example.movietime.controllers;

import org.example.movietime.dto.DettaglioCarrelloDTO;
import org.example.movietime.entities.Carrello;
import org.example.movietime.entities.Cliente;
import org.example.movietime.exceptions.*;
import org.example.movietime.repositories.ClienteRepository;
import org.example.movietime.services.CarrelloService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/carrello")
public class CarrelloController {

    private final CarrelloService carrelloService;

    public CarrelloController(CarrelloService carrelloService){
        this.carrelloService=carrelloService;
    }

    @GetMapping("/dettagli-carrello")
    public ResponseEntity<List<DettaglioCarrelloDTO>> getDettagliCarrelloOrdinati(
            @RequestParam int idCliente,
            @RequestParam(name = "p", defaultValue = "0") int pageNumber,
            @RequestParam String sortBy,
            @RequestParam String order) {
        List<DettaglioCarrelloDTO> dettagliCarrello = null;
        try {
            dettagliCarrello = carrelloService.getDettagliCarrelloOrdinati(idCliente, pageNumber, sortBy, order);
        } catch (CarrelloNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(dettagliCarrello);
    }

    @GetMapping("/dettaglio")
    public ResponseEntity<DettaglioCarrelloDTO> getDettaglioCarrello(
            @RequestParam int idDettaglioCarrello,
            @RequestParam int idCliente) {
        try {
            DettaglioCarrelloDTO dettaglioCarrelloDTO = carrelloService.getSingleDettaglioCarrello(idDettaglioCarrello,idCliente);
            return ResponseEntity.ok(dettaglioCarrelloDTO);
        } catch (FilmNotFoundException | CarrelloNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/aggiungi")
    public ResponseEntity<String> aggiungiAlCarrello(
            @RequestParam int idCliente,
            @RequestParam String titolo,
            @RequestParam String formato,
            @RequestParam int quantity) {
        try {
            carrelloService.aggiungiAlCarrello(idCliente, titolo, formato, quantity);
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


}
