package tn.esprit.projetservice.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.projetservice.entities.Project;

@Repository
public interface ProjectRepository extends MongoRepository<Project, String> {
}
