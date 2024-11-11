package org.example.movietime.entities;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "carrello")
public class Carrello {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_carrello", nullable = false)
    private int idCarrello;

    //RELAZIONI
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;
}
