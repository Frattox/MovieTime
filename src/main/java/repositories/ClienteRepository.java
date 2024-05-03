package repositories;

import entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//Con l'annotazione repository si ottengono anche i metodi di base CRUD (Create, Read, Update, Delete)
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

}
