package tn.esprit.saroua.feedsyncservice.repositories;

import tn.esprit.saroua.feedsyncservice.entities.ReponseSondage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReponseSondageRepository extends MongoRepository<ReponseSondage, String> {
}
