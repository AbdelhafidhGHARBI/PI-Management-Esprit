package tn.esprit.spring.tacheservice.repositories;


import org.springframework.data.mongodb.repository.MongoRepository;
import tn.esprit.spring.tacheservice.entities.Priorite;
import tn.esprit.spring.tacheservice.entities.Status;
import tn.esprit.spring.tacheservice.entities.Tache;

import java.util.Date;
import java.util.List;


public interface tacheRepository extends MongoRepository<Tache, String> {
    List<Tache> findByProjetId(String projetId); // Pour F05
    List<Tache> findByAssigneeId(String assigneeId); // Pour F07
    List<Tache> findByEquipeId(String equipeId); // Pour F06
    List<Tache> findByStatus(Status status); // Pour F08
    List<Tache> findByPriorite(Priorite priorite); // Pour F09
    List<Tache> findByDeadlineBefore(Date date); // Pour F10
    List<Tache> findByProjetIdAndStatus(String projetId, Status status); // Pour F14

    // Recherche avancée
    List<Tache> findByProjetIdAndStatusAndPriorite(String projetId, Status status, Priorite priorite);
    List<Tache> findByAssigneeIdAndDeadlineBefore(String assigneeId, Date deadline);

}