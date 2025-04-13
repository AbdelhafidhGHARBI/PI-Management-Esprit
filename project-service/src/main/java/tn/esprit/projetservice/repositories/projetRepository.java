package tn.esprit.projetservice.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.projetservice.entities.Projet;

@Repository
public interface projetRepository extends MongoRepository<Projet, String> {
}
