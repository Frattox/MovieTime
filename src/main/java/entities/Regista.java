package entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;
import java.util.List;
import java.util.Objects;

@Setter
@Getter
@EqualsAndHashCode
@ToString
@Entity
public class Regista {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ID_regista", nullable = false)
    private int idRegista;
    @Basic
    @Column(name = "Nome", nullable = false, length = -1)
    private String nome;
    @Basic
    @Column(name = "Cognome", nullable = false, length = -1)
    private String cognome;
    @Basic
    @Column(name = "Data_N", nullable = false)
    private Date dataN;
    @Basic
    @Column(name = "Nazionalita", nullable = false, length = -1)
    private String nazionalita;

    //RELAZIONI
    @OneToMany(mappedBy = "regista", cascade = CascadeType.ALL)
    private List<Film> filmografia;

}
