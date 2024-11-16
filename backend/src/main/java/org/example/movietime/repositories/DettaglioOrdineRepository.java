package org.example.movietime.repositories;

import org.example.movietime.entities.DettaglioOrdine;
import org.example.movietime.entities.Ordine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DettaglioOrdineRepository extends JpaRepository<DettaglioOrdine, Integer> {

    Page<DettaglioOrdine> findAllByOrdine(Ordine ordine, Pageable pageable);

}
