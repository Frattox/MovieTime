package dto;

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

    @NotBlank(message = "L'username non può essere vuoto")
    @Size(min = 1, max = 50, message = "L'username deve avere tra 1 e 50 caratteri")
    private String username;

    @NotBlank(message = "L'email non può essere vuota")
    @Email(message = "Deve essere un'email valida")
    @Size(max = 100, message = "L'email non può superare i 100 caratteri")
    private String email;

    @NotBlank(message = "La password non può essere vuota")
    @Size(min = 8, max = 100, message = "La password deve avere tra 8 e 100 caratteri")
    private String password;

    @NotNull(message = "La data di registrazione non può essere null")
    private Date dataRegistrazione;
}
