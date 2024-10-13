package org.example.movietime.exceptionHandler;

import org.example.movietime.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(FilmNotFoundException.class)
    public ResponseEntity<String> handleFilmNotFound(FilmNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Film non trovato");
    }
    @ExceptionHandler(FilmWornOutException.class)
    public ResponseEntity<String> handleFilmNotFound(FilmWornOutException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Film non disponibile");
    }

    @ExceptionHandler(ClienteNotFoundException.class)
    public ResponseEntity<String> handleClienteNotFoundException(ClienteNotFoundException ex) {
        return new ResponseEntity<>("Cliente non trovato", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DettaglioCarrelloNotFoundException.class)
    public ResponseEntity<String> handleDettaglioCarrelloNotFoundException(DettaglioCarrelloNotFoundException ex) {
        return new ResponseEntity<>("Dettaglio carrello non trovato", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DettaglioOrdineNotFoundException.class)
    public ResponseEntity<String> handleDettaglioOrdineNotFoundException(DettaglioOrdineNotFoundException ex) {
        return new ResponseEntity<>("Dettaglio ordine non trovato", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmptyCartException.class)
    public ResponseEntity<String> handleEmptyCartException(EmptyCartException ex) {
        return new ResponseEntity<>("Il carrello è vuoto", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OrdineNotFoundException.class)
    public ResponseEntity<String> handleOrdineNotFoundException(OrdineNotFoundException ex) {
        return new ResponseEntity<>("Ordine non trovato", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RegistaNotFoundException.class)
    public ResponseEntity<String> handleRegistaNotFoundException(RegistaNotFoundException ex) {
        return new ResponseEntity<>("Regista non trovato", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotLoggedException.class)
    public ResponseEntity<String> handleUserNotLoggedException(UserNotLoggedException ex) {
        return new ResponseEntity<>("Utente non loggato", HttpStatus.UNAUTHORIZED);
    }
}
