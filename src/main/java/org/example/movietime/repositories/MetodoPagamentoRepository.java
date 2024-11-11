package org.example.movietime.repositories;

import org.example.movietime.entities.Cliente;
import org.example.movietime.entities.MetodoPagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MetodoPagamentoRepository extends JpaRepository<MetodoPagamento,Integer> {

    Optional<MetodoPagamento> findByIdMetodoPagamentoAndCliente(int idMetodoDiPagamento, Cliente cliente);

    List<MetodoPagamento> findAllByCliente(Cliente cliente);

}
