package org.example.movietime.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Data specifica setter, getter, toString, equals e hashCode
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Film {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_film", nullable = false)
    private int idFilm;
    @Basic
    @Column(name = "titolo", nullable = false, length = -1)
    private String titolo;
    @Basic
    @Column(name = "anno_uscita", nullable = false, precision = 0)
    private int annoUscita;
    @Basic
    @Column(name = "genere", nullable = false, length = -1)
    private String genere;
    @Basic
    @Column(name = "formato", nullable = false, length = -1)
    private String formato;
    @Basic
    @Column(name = "prezzo", nullable = false, precision = 0)
    private float prezzo;
    @Basic
    @Column(name = "quantita", nullable = false, precision = 0)
    private int quantita;

    @Version
    @Basic
    @Column(name = "versione", nullable = false, precision = 0)
    private long versione;

    //RELAZIONI
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_regista", referencedColumnName = "id_regista", nullable = false)
    private Regista regista;

}
