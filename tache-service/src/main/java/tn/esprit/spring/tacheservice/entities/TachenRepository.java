package tn.esprit.spring.tacheservice.entities;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface TachenRepository extends MongoRepository<Tache, String> {
}
