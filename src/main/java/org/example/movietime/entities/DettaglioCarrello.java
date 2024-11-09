package org.example.movietime.entities;

import jakarta.persistence.*;
import lombok.*;

//@Data specifica setter, getter, toString, equals e hashCode
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "dettaglio_carrello")
public class DettaglioCarrello {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_dettaglio_carrello", nullable = false)
    private int idDettaglioCarrello;
    @Basic
    @Column(name = "quantita", nullable = false, precision = 0)
    private int quantita;
    @Basic
    @Column(name = "prezzo_unita", nullable = false, precision = 0)
    private float prezzoUnita;

    //RELAZIONI
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_carrello", nullable = false)
    private Carrello carrello;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_film", nullable = false)
    private Film film;
}
