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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    //RELAZIONI
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    @OneToOne
    @JoinColumn(name = "id_carrello", nullable = false)
    private Carrello carrello;

    @ManyToOne
    @JoinColumn(name = "id_metodo_pagamento", nullable = false)
    private MetodoPagamento metodoPagamento;

    @OneToMany(mappedBy = "ordine", cascade = CascadeType.ALL)
    private List<DettaglioOrdine> dettagliOrdini;

}
