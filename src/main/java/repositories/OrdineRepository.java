package repositories;

import entities.Cliente;
import entities.DettaglioCarrello;
import entities.Ordine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrdineRepository extends JpaRepository<Ordine,Integer> {

    Page<Ordine> findByClienteAndDataOrdineBetween(Cliente cliente, LocalDateTime min, LocalDateTime max, Pageable pageable);

    @Query("SELECT o FROM Ordine o WHERE o.cliente.idCliente=:idCliente ORDER BY o.dataOrdine DESC")
    Page<Ordine> findAllOrderByDataDesc(@Param("idCliente") int idCliente, Pageable pageable);

    @Query("SELECT o FROM Ordine o WHERE o.cliente.idCliente=:idCliente ORDER BY o.dataOrdine ASC")
    Page<Ordine> findAllOrderByDataAsc(@Param("idCliente") int idCliente, Pageable pageable);


}
