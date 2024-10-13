package org.example.movietime.repositories;

import org.example.movietime.entities.Carrello;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarrelloRepository extends JpaRepository<Carrello, Integer> {

}
