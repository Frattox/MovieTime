package org.example.movietime.entities;

import jakarta.persistence.*;
import lombok.*;

//@Data specifica setter, getter, toString, equals e hashCode
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "dettaglio_ordine")
public class DettaglioOrdine {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_dettaglio_ordine", nullable = false)
    private int idDettaglioOrdine;
    @Basic
    @Column(name = "quantita", nullable = false, precision = 0)
    private int quantita;
    @Basic
    @Column(name = "prezzo_unita", nullable = false, precision = 0)
    private float prezzoUnita;

    //RELAZIONI
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_ordine", nullable = false)
    private Ordine ordine;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_film", nullable = false)
    private Film film;
}
