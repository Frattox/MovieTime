package org.example.movietime.dto;

import org.example.movietime.entities.Regista;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilmDTO{

    @Min(1)
    private int idFilm;

    @NotBlank(message = "Il titolo non può essere vuoto")
    @Size(min = 1, max = 100, message = "Il titolo deve avere tra 1 e 100 caratteri")
    private String titolo;

    @Min(value = 1888, message = "L'anno di uscita deve essere almeno 1888")
    @Max(value = 2100, message = "L'anno di uscita deve essere realistico")
    private int annoUscita;

    @NotBlank(message = "Il genere non può essere vuoto")
    @Size(min = 1, max = 50, message = "Il genere deve avere tra 1 e 50 caratteri")
    private String genere;

    @NotBlank(message = "Il formato non può essere vuoto")
    @Size(min = 1, max = 20, message = "Il formato deve avere tra 1 e 20 caratteri")
    private String formato;

    @DecimalMin(value = "0.01", message = "Il prezzo deve essere maggiore di 0")
    @Digits(integer = 6, fraction = 2, message = "Il prezzo non può avere più di 6 cifre intere e 2 decimali")
    private float prezzo;

    @Min(value = 0, message = "La quantità non può essere negativa")
    private int quantita;

    @NotNull(message = "Il regista non può essere null")
    private Regista regista;
}
