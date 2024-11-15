package org.example.movietime.exceptionHandler.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DettaglioCarrelloDTO {

    @Min(1)
    private int idDettaglioCarrello;

    @Min(value = 1, message = "La quantità deve essere almeno 1")
    private int quantita;

    @DecimalMin(value = "0.01", message = "Il prezzo unitario deve essere maggiore di 0")
    private float prezzoUnita;

    @Min(value = 1, message = "L'ID del carrello deve essere valido")
    private int carrelloId;

    @Min(value = 1, message = "L'ID del film deve essere valido")
    private int filmId;
}
