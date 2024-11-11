package org.example.movietime.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDTO {

    @Min(1)
    private int idCliente;

    @NotBlank(message = "Il nome non può essere vuoto")
    @Size(min = 1, max = 100, message = "Il nome deve avere tra 1 e 100 caratteri")
    private String nome;

    @NotBlank(message = "Il cognome non può essere vuoto")
    @Size(min = 1, max = 100, message = "Il cognome deve avere tra 1 e 100 caratteri")
    private String cognome;

    @NotBlank(message = "L'email non può essere vuota")
    @Email(message = "Deve essere un'email valida")
    @Size(max = 100, message = "L'email non può superare i 100 caratteri")
    private String email;

    @NotNull(message = "La data di registrazione non può essere null")
    private Date dataRegistrazione;
}
