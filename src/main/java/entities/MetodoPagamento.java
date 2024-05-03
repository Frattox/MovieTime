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
    private BigInteger numero;

    //RELAZIONI
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_cliente", nullable = false)
    private Cliente metodoPagamentoCliente;

}
