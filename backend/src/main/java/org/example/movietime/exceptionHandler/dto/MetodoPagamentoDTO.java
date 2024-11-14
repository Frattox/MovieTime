package org.example.movietime.exceptionHandler.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MetodoPagamentoDTO {

    @Min(1)
    private int idMetodoPagamento;

    @NotBlank(message = "Il tipo non può essere vuoto")
    private String tipo;

    @Min(value = 1, message = "Il numero deve essere positivo")
    private int numero;

    @Min(value = 1,message = "Il cliente deve essere valido")
    private int clienteId;
}
