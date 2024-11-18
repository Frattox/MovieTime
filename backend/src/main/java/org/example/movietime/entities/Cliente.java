package org.example.movietime.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Table(name = "cliente")
@Data
@Entity
@EntityListeners(AuditingEntityListener.class) //per la data registrazione
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_cliente", nullable = false)
    private int idCliente;

    @Basic
    @Column(name = "nome", nullable = false, length = -1)
    private String nome;
    @Basic
    @Column(name = "cognome", nullable = false, length = -1)
    private String cognome;
    @Basic
    @Column(name = "email",unique = true ,nullable = false, length = -1)
    private String email;

    @CreatedDate //così crea automaticamente la data al momento della registrazione
    @Basic
    @Column(name = "data_registrazione", nullable = false)
    private LocalDate dataRegistrazione;
}
