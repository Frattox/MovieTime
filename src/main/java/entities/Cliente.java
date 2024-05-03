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
public class Cliente {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ID_cliente", nullable = false)
    private int idCliente;

    @Basic
    @Column(name = "Nome", nullable = false, length = -1)
    private String nome;
    @Basic
    @Column(name = "Cognome", nullable = false, length = -1)
    private String cognome;
    @Basic
    @Column(name = "Email", nullable = false, length = -1)
    private String email;
    @Basic
    @Column(name = "Password", nullable = false, length = -1)
    private String password;
    @Basic
    @Column(name = "Data_registrazione", nullable = false)
    private Date dataRegistrazione;

    //RELAZIONI
    @OneToOne(mappedBy = "cliente")
    private Carrello carrello;

    @OneToMany(mappedBy = "cliente" ,cascade = CascadeType.ALL)
    private List<Ordine> ordini;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<MetodoPagamento> metodiPagamento;



}
