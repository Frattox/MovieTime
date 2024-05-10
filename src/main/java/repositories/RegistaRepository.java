package repositories;

import entities.Regista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegistaRepository extends JpaRepository<Regista,Integer> {

    Regista findByNomeAndCognome(String nome, String cognome);

    List<Regista> findByNome(String nome);

    List<Regista> findByCognome(String cognome);

    //optional
    List<Regista> findByNazionalita(String nazionalita);

}
