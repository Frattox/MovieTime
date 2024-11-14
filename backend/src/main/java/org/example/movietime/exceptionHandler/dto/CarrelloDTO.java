package org.example.movietime.exceptionHandler.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarrelloDTO {
    @NotNull
    private List<Integer> idDettagliCarrello;
    @NotNull
    private List<Integer> quantita;
    @NotNull
    private List<Float> prezzi;
}
