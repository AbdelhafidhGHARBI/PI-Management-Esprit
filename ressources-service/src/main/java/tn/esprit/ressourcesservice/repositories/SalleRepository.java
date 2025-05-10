package tn.esprit.ressourcesservice.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.ressourcesservice.entities.Salle;
import tn.esprit.ressourcesservice.entities.TypeSalle;

import java.util.List;
@Repository
public interface SalleRepository extends MongoRepository<Salle, String> {
    List<Salle> findByDisponibleTrue();

    List<Salle> findByType(TypeSalle type);

    List<Salle> findByCapaciteGreaterThanEqual(int capacite);

    boolean existsByNom(String nom);
}
