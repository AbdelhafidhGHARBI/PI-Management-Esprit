package tn.esprit.saroua.feedsyncservice.repositories;

import tn.esprit.saroua.feedsyncservice.entities.Reclamation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReclamationRepository extends MongoRepository<Reclamation, String> {
}
