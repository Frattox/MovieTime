package org.example.movietime.entities;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "Carrello")
public class Carrello {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "cliente", nullable = false)
    private int idCarrello;

    //RELAZIONI
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cliente", nullable = false)
    private Cliente cliente;

}
