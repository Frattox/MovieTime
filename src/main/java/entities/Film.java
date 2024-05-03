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
public class Film {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ID_film", nullable = false)
    private int idFilm;
    @Basic
    @Column(name = "Titolo", nullable = false, length = -1)
    private String titolo;
    @Basic
    @Column(name = "Anno_uscita", nullable = false, precision = 0)
    private BigInteger annoUscita;
    @Basic
    @Column(name = "Genere", nullable = false, length = -1)
    private String genere;
    @Basic
    @Column(name = "Formato", nullable = false, length = -1)
    private String formato;
    @Basic
    @Column(name = "Prezzo", nullable = false, precision = 0)
    private float prezzo;

    @Version
    @Basic
    @Column(name = "Versione", nullable = false, precision = 0)
    private long versione;

    //RELAZIONI
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_regista", nullable = false)
    private Regista regista;

}
