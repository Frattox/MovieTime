package dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistaDTO {

    @Min(1)
    private int idRegista;

    @NotBlank(message = "Il nome non può essere vuoto")
    @Size(min = 2, max = 100, message = "Il nome deve essere tra 2 e 100 caratteri")
    private String nome;

    @NotBlank(message = "Il cognome non può essere vuoto")
    @Size(min = 2, max = 100, message = "Il cognome deve essere tra 2 e 100 caratteri")
    private String cognome;

    @NotNull(message = "La data di nascita è obbligatoria")
    @Past(message = "La data di nascita deve essere nel passato")
    private Date dataN;

    @NotBlank(message = "La nazionalità non può essere vuota")
    @Size(min = 2, max = 100, message = "La nazionalità deve essere tra 2 e 100 caratteri")
    private String nazionalita;
}
