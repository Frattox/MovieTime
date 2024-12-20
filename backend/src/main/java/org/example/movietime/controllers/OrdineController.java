package org.example.movietime.controllers;

import org.example.movietime.dto.CarrelloDTO;
import org.example.movietime.dto.DettaglioOrdineDTO;
import org.example.movietime.dto.OrdineDTO;
import org.example.movietime.exceptions.*;
import org.example.movietime.services.ClienteService;
import org.example.movietime.services.OrdineService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidParameterException;
import java.util.List;

@RestController
@RequestMapping("/ordini")
public class OrdineController {
    private final OrdineService ordineService;
    private final ClienteService clienteService;

    public OrdineController(OrdineService ordineService, ClienteService clienteService) {
        this.ordineService = ordineService;
        this.clienteService = clienteService;
    }


    @PostMapping("/acquistaDalCarrello")
    public ResponseEntity<String> acquistaDalCarrello(
            @RequestParam String indirizzo,
            @RequestParam int numero,
            @RequestBody CarrelloDTO carrelloDTO
            ) {
        try {
            Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String email = (String) jwt.getClaims().get("email");
            int idCliente = clienteService.getProfile(email).getIdCliente();
            ordineService.acquistaDalCarrello(
                    idCliente,
                    indirizzo,
                    numero,
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

    @PostMapping("/acquistaFilm")
    public ResponseEntity<String> acquistaFilm(
            @RequestParam int idFilm,
            @RequestParam int quantity,
            @RequestParam String indirizzo,
            @RequestParam int idMetodoDiPagamento) {

        try {
            Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String email = (String) jwt.getClaims().get("email");
            int idCliente = clienteService.getProfile(email).getIdCliente();
            ordineService.acquistaFilm(idCliente, idFilm, quantity, indirizzo, idMetodoDiPagamento);
            return ResponseEntity.ok("Ordine creato con successo");
        } catch (ClienteNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente non trovato");
        } catch (FilmNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Film non trovato");
        } catch (FilmWornOutException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Quantità di film non disponibile");
        } catch (CarrelloNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Carrello non trovato");
        } catch (MetodoDiPagamentoNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Metodo di pagamento non trovato");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore nell'elaborazione dell'ordine");
        }
    }

    @GetMapping
    public ResponseEntity<List<OrdineDTO>> getOrdiniPaged(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "") String order) {
        try {
            Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String email = (String) jwt.getClaims().get("email");
            System.out.println((String) jwt.getClaims().get("email"));
            int idCliente = clienteService.getProfile(email).getIdCliente();
            List<OrdineDTO> ordini = ordineService.getAllOrdiniPaged(idCliente, pageNumber, order);
            return ResponseEntity.ok(ordini);
        } catch (ClienteNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    @GetMapping("/singolo")
    public ResponseEntity<OrdineDTO> getSingleOrdine(
            @RequestParam int idOrdine
    ) {
        try {
            Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String email = (String) jwt.getClaims().get("email");
            int idCliente = clienteService.getProfile(email).getIdCliente();
            OrdineDTO ordineDTO = ordineService.getSingleOrdine(idOrdine, idCliente);
            return ResponseEntity.ok(ordineDTO);
        } catch (OrdineNotFoundException | ClienteNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/dettagli-ordine")
    public ResponseEntity<List<DettaglioOrdineDTO>> getDettagliOrdine(
            @RequestParam int idOrdine,
            @RequestParam(defaultValue = "0") int pageNumber
    ){
        try {
            Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String email = (String) jwt.getClaims().get("email");
            System.out.println((String) jwt.getClaims().get("email"));
            int idCliente = clienteService.getProfile(email).getIdCliente();
            List<DettaglioOrdineDTO> dettagliOrdine = ordineService.getDettagliOrdine(idOrdine,idCliente, pageNumber);
            return ResponseEntity.ok(dettagliOrdine);
        } catch (OrdineNotFoundException | ClienteNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
