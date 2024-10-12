package entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigInteger;
import java.util.Objects;

//@Data specifica setter, getter, toString, equals e hashCode
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class MetodoPagamento {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ID_metodo_pagamento", nullable = false)
    private int idMetodoPagamento;
    @Basic
    @Column(name = "Tipo", nullable = false, length = -1)
    private String tipo;
    @Basic
    @Column(name = "Numero", nullable = false, precision = 0)
    private int numero;

    //RELAZIONI
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_cliente", nullable = false)
    private Cliente metodoPagamentoCliente;
}
