package tn.esprit.ressourcesservice.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.ressourcesservice.entities.Classe;

import java.util.List;
@Repository
public interface ClasseRepository extends MongoRepository<Classe, String> {
    List<Classe> findByFormationAndNiveau(String formation, String niveau);

    List<Classe> findByAnneeAcademique(String anneeAcademique);

    List<Classe> findBySpecialite(String specialite);

    boolean existsByName(String name);
}
