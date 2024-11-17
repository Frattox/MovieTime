package org.example.movietime.controllers;

import org.example.movietime.exceptions.RegistaNotFoundException;
import org.example.movietime.dto.FilmDTO;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.example.movietime.dto.RegistaDTO;
import org.example.movietime.services.RegistaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.example.movietime.services.FilmService;
import org.example.movietime.exceptions.FilmNotFoundException;

import java.util.List;

@RestController
@RequestMapping("/film")
@Validated
public class FilmController {

    private final FilmService filmService;
    private final RegistaService registaService;

    public FilmController(FilmService filmService, RegistaService registaService) {
        this.filmService = filmService;
        this.registaService = registaService;
    }

    // Endpoint per ottenere tutti i film con paginazione
    @GetMapping
    public ResponseEntity<List<FilmDTO>> getAllFilms(
            @RequestParam(defaultValue = "0") @Min(0) int page) {
        List<FilmDTO> films = filmService.getAllFilms(page);
        return ResponseEntity.ok(films);
    }


    @GetMapping("/{id}")
    public ResponseEntity<FilmDTO> getFilm(
            @PathVariable @Min(1) int id
    ){
        FilmDTO film;
        try {
            film = filmService.getFilm(id);
        } catch (FilmNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(film);
    }

    @GetMapping("/search")
    public ResponseEntity<List<FilmDTO>> searchFilms(
            @RequestParam @NotBlank String title,
            @RequestParam(defaultValue = "0") @Min(0) int page) {
        List<FilmDTO> films = filmService.getAllFilmsLike(title, page);
        return ResponseEntity.ok(films);
    }

    @GetMapping("/regista")
    public ResponseEntity<RegistaDTO> getRegista(@RequestParam int idRegista) {
        try {
            RegistaDTO registaDTO = registaService.getRegista(idRegista);
            return ResponseEntity.ok(registaDTO);
        } catch (RegistaNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

}
