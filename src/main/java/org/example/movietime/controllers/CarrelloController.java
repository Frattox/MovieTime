package org.example.movietime.controllers;

import org.example.movietime.dto.DettaglioCarrelloDTO;
import org.example.movietime.entities.Carrello;
import org.example.movietime.entities.Cliente;
import org.example.movietime.exceptions.CarrelloNotFoundException;
import org.example.movietime.exceptions.ClienteNotFoundException;
import org.example.movietime.exceptions.FilmNotFoundException;
import org.example.movietime.exceptions.FilmWornOutException;
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
    private final ClienteRepository clienteRepository;

    public CarrelloController(CarrelloService carrelloService, ClienteRepository clienteRepository){this.carrelloService=carrelloService;
        this.clienteRepository = clienteRepository;
    }

    @GetMapping("/dettagli-carrello/ordinati")
    public ResponseEntity<List<DettaglioCarrelloDTO>> getDettagliCarrelloOrdinati(
            @RequestParam int idCliente,
            @RequestParam(name = "p", defaultValue = "0") int pageNumber,
            @RequestParam String sortBy,
            @RequestParam String order) {

        Optional<Carrello> carrello = carrelloService.findByIdCliente(idCliente);

        if (carrello.isEmpty()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // Restituisce 403 se il carrello non è del cliente
        }

        List<DettaglioCarrelloDTO> dettagliCarrello = carrelloService.getDettagliCarrelloOrdinati(carrello.get(), pageNumber, sortBy, order);
        return ResponseEntity.ok(dettagliCarrello);
    }

    @PostMapping("/aggiungi-al-carrello")
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
