package tn.esprit.saroua.feedsyncservice.repositories;


import tn.esprit.saroua.feedsyncservice.entities.Sondage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SondageRepository extends MongoRepository<Sondage, String> {
}

