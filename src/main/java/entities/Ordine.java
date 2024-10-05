package entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

//@Data specifica setter, getter, toString, equals e hashCode
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Ordine {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ID_ordine", nullable = false)
    private int idOrdine;

    @Basic
    @Column(name = "Data_ordine", nullable = false)
    private LocalDateTime dataOrdine;
    @Basic
    @Column(name = "Stato", nullable = false, length = -1)
    private String stato;
    @Basic
    @Column(name = "Indirizzo", nullable = false, length = -1)
    private String indirizzo;

    //RELAZIONI
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_cliente", nullable = false)
    private Cliente ordineCliente;

    @OneToOne
    @JoinColumn(name = "ID_carrello", nullable = false)
    private Carrello ordineCarrello;

    @ManyToOne
    @JoinColumn(name = "ID_metodo_pagamento", nullable = false)
    private MetodoPagamento metodoPagamento;

    @OneToMany(mappedBy = "ordine", cascade = CascadeType.ALL)
    private List<DettaglioOrdine> dettagliOrdini;

}
