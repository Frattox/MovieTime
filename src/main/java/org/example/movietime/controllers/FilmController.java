package org.example.movietime.controllers;

import org.example.movietime.mapper.dto.FilmDTO;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    // Endpoint per ottenere tutti i film con paginazione
    @GetMapping
    public ResponseEntity<List<FilmDTO>> getAllFilms(
            @RequestParam(defaultValue = "0") @Min(0) int page) {
        List<FilmDTO> films = filmService.getAllFilms(page);
        return ResponseEntity.ok(films);
    }

    // Endpoint per ottenere i dettagli di un singolo film
    @GetMapping("/{id}")
    public ResponseEntity<FilmDTO> getFilm(
            @PathVariable @Min(1) int id) throws FilmNotFoundException {
        FilmDTO film = filmService.getFilm(id);
        return ResponseEntity.ok(film);
    }

    // Endpoint per cercare i film per titolo con paginazione
    @GetMapping("/search")
    public ResponseEntity<List<FilmDTO>> searchFilms(@RequestParam @NotBlank String title, @RequestParam(defaultValue = "0") @Min(0) int page) {
        List<FilmDTO> films = filmService.getAllFilmsLike(title, page);
        return ResponseEntity.ok(films);
    }

}
