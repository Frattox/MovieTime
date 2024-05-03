package entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Cascade;

import java.util.List;
import java.util.Objects;

@Setter
@Getter
@EqualsAndHashCode
@ToString
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
