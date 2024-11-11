package org.example.movietime.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Date;
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
    @Column(name = "email", nullable = false, length = -1)
    private String email;
    @Basic
    @Column(name = "password", nullable = false, length = -1)
    private String password;

    @CreatedDate //così crea automaticamente la data al momento della registrazione
    @Basic
    @Column(name = "data_registrazione", nullable = false)
    private Date dataRegistrazione;
}
