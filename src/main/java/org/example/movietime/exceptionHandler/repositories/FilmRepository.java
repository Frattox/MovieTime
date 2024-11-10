package org.example.movietime.exceptionHandler.repositories;

import org.example.movietime.entities.Film;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FilmRepository extends JpaRepository<Film, Integer> {

    Optional<Film> findByTitoloAndFormato(String titolo, String formato);

    @Query("SELECT f FROM Film f WHERE LOWER(f.titolo) LIKE LOWER(CONCAT('%', :titolo, '%'))")
    Page<Film> findAllByTitolo(@Param("titolo") String titolo, Pageable pageable);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT f FROM Film f WHERE f.idFilm = :id")
    Optional<Film> findByIdWithLock(@Param("id") int id);


}
