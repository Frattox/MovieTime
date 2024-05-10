package entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigInteger;
import java.util.Objects;

//@Data specifica setter, getter, toString, equals e hashCode
@Data
@Entity
public class DettaglioCarrello {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ID_dettaglio_carrello", nullable = false)
    private int idDettaglioCarrello;
    @Basic
    @Column(name = "Quantita", nullable = false, precision = 0)
    private int quantita;
    @Basic
    @Column(name = "Prezzo_unita", nullable = false, precision = 0)
    private float prezzoUnita;

    //RELAZIONI
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_carrello", nullable = false)
    private Carrello carrello;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_film", nullable = false)
    private Film film;
}
