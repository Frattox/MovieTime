package org.example.movietime.entities;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.util.List;

//@Data specifica setter, getter, toString, equals e hashCode
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "regista")
public class Regista {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_regista", nullable = false)
    private int idRegista;
    @Basic
    @Column(name = "nome", nullable = false, length = -1)
    private String nome;
    @Basic
    @Column(name = "cognome", nullable = false, length = -1)
    private String cognome;
    @Basic
    @Column(name = "data_n", nullable = false)
    private Date dataN;
    @Basic
    @Column(name = "nazionalita", nullable = false, length = -1)
    private String nazionalita;

}
