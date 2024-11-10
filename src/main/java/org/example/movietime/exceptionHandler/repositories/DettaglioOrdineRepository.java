package org.example.movietime.exceptionHandler.repositories;

import org.example.movietime.entities.DettaglioOrdine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DettaglioOrdineRepository extends JpaRepository<DettaglioOrdine, Integer> {

    List<DettaglioOrdine> findByPrezzoUnitaGreaterThan(float prezzo);

    List<DettaglioOrdine> findByPrezzoUnitaLessThan(float prezzo);

}
