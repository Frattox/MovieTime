package entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;

import java.util.List;
import java.util.Objects;

//@Data specifica setter, getter, toString, equals e hashCode
@Data
@Entity
public class Carrello {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ID_carrello", nullable = false)
    private int idCarrello;

    //RELAZIONI
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_cliente", nullable = false)
    private Cliente carrelloCliente;

    @OneToOne(mappedBy = "carrello")
    private Ordine carrelloOrdine;

    @OneToMany(mappedBy = "carrello", cascade = CascadeType.ALL)
    private List<DettaglioCarrello> dettagliCarrello;

}
