package tn.esprit.ressource_service.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.ressource_service.entities.classeEntity;

@Repository
public interface classeeRepository extends MongoRepository<classeEntity, String> {

}
