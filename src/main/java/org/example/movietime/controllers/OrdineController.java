package org.example.movietime.controllers;

import org.example.movietime.dto.CarrelloDTO;
import org.example.movietime.exceptions.*;
import org.example.movietime.services.OrdineService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidParameterException;
import java.util.List;

@RestController
@RequestMapping("/ordini")
public class OrdineController {
    private final OrdineService ordineService;

    public OrdineController(OrdineService ordineService) {
        this.ordineService = ordineService;
    }

    @PostMapping("/acquista")
    public ResponseEntity<String> acquistaDalCarrello(
            @RequestParam int idCliente,
            @RequestParam String indirizzo,
            @RequestParam int idMetodoDiPagamento,
            @RequestBody CarrelloDTO carrelloDTO
            ) {
        try {
            ordineService.acquistaDalCarrello(
                    idCliente,
                    indirizzo,
                    idMetodoDiPagamento,
                    carrelloDTO
            );
            return ResponseEntity.status(HttpStatus.CREATED).body("Ordine creato con successo");
        } catch (FilmWornOutException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Film non disponibile in quantità richiesta");
        } catch (ClienteNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente non trovato");
        } catch (FilmNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Film non trovato");
        } catch (DettaglioCarrelloNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Dettaglio carrello non trovato");
        } catch (CarrelloNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Carrello non trovato");
        } catch (MetodoDiPagamentoNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Metodo di pagamento non trovato");
        } catch (InvalidParameterException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
