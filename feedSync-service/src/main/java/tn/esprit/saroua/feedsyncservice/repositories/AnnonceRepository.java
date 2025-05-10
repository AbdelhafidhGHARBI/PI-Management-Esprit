package tn.esprit.saroua.feedsyncservice.repositories;


import tn.esprit.saroua.feedsyncservice.entities.Annonce;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnonceRepository extends MongoRepository<Annonce, String> {
    // Tu peux ajouter des méthodes personnalisées ici si besoin
}
