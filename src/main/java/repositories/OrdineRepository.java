package repositories;

import entities.Ordine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrdineRepository extends JpaRepository<Ordine,Integer> {

    List<Ordine> findByStato(String stato);

    List<Ordine> findByDataOrdineBefore(Date max);

    List<Ordine> findByDataOrdineAfter(Date min);

    List<Ordine> findByDataOrdineBetween(Date min, Date max);
}
