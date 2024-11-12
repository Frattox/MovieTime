package org.example.movietime.entities;

import jakarta.persistence.*;
import lombok.*;

//@Data specifica setter, getter, toString, equals e hashCode
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "metodo_pagamento")
public class MetodoPagamento {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_metodo_pagamento", nullable = false)
    private int idMetodoPagamento;
    @Basic
    @Column(name = "tipo", nullable = false, length = -1)
    private String tipo;
    @Basic
    @Column(name = "numero", nullable = false, precision = 0)
    private int numero;

    //RELAZIONI
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;
}
