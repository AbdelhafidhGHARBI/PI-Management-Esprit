package tn.esprit.ressourcesservice.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.ressourcesservice.entities.Ressource;
import tn.esprit.ressourcesservice.entities.TypeRessource;

import java.util.List;

@Repository
public interface RessourceRepository extends MongoRepository<Ressource, String> {
    List<Ressource> findByType(TypeRessource type);

    List<Ressource> findByNomContainingIgnoreCase(String keyword);

    //List<Ressource> findByDisponibleTrue();

    boolean existsByNom(String nom);
}
