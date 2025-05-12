package tn.esprit.document_service.repositories;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.document_service.entities.Doc;

@Repository
public interface DocumentRepository extends MongoRepository<Doc, String> {

}
