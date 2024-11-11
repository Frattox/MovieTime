package org.example.movietime.mapper.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdineDTO {

    private int idOrdine;

    @NotNull(message = "La data dell'ordine non può essere nulla")
    private LocalDateTime dataOrdine;

    @NotBlank(message = "Lo stato non può essere vuoto")
    private String stato;

    @NotBlank(message = "L'indirizzo non può essere vuoto")
    private String indirizzo;
}
