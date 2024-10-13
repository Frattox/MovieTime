package org.example.movietime.entities;

import jakarta.persistence.*;
import lombok.*;

//@Data specifica setter, getter, toString, equals e hashCode
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class DettaglioOrdine {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ID_dettaglio_ordine", nullable = false)
    private int idDettaglioOrdine;
    @Basic
    @Column(name = "Quantita", nullable = false, precision = 0)
    private int quantita;
    @Basic
    @Column(name = "Prezzo_unita", nullable = false, precision = 0)
    private float prezzoUnita;

    //RELAZIONI
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_ordine", nullable = false)
    private Ordine ordine;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_film", nullable = false)
    private Film film;
}
