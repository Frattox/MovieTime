package org.example.movietime.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

//@Data specifica setter, getter, toString, equals e hashCode
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ordine")
public class Ordine {
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Id
    @Column(name = "id_ordine", nullable = false)
    private int idOrdine;

    @Basic
    @Column(name = "data_ordine", nullable = false)
    private LocalDateTime dataOrdine;
    @Basic
    @Column(name = "stato", nullable = false, length = -1)
    private String stato;
    @Basic
    @Column(name = "indirizzo", nullable = false, length = -1)
    private String indirizzo;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_carrello", nullable = false)
    private Carrello carrello;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_metodo_pagamento", nullable = false)
    private MetodoPagamento metodoPagamento;

}
