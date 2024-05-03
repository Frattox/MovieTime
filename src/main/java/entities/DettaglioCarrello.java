package entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigInteger;
import java.util.Objects;

@Setter
@Getter
@EqualsAndHashCode
@ToString
@Entity
public class DettaglioCarrello {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ID_dettaglio_carrello", nullable = false)
    private int idDettaglioCarrello;
    @Basic
    @Column(name = "Quantita", nullable = false, precision = 0)
    private BigInteger quantita;
    @Basic
    @Column(name = "Prezzo_unita", nullable = false, precision = 0)
    private BigInteger prezzoUnita;

    //RELAZIONI
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_carrello", nullable = false)
    private Carrello carrello;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_film", nullable = false)
    private Film film;
}
