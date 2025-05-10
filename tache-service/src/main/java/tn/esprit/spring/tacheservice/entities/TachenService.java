package tn.esprit.spring.tacheservice.entities;

import java.util.List;
import java.util.Optional;

public interface TachenService {
    tachen createTache(tachen tache);
    List<tachen> getAllTaches();
    Optional<tachen> getTacheById(String id);
    Optional<tachen> updateTache(String id, tachen updatedTache);
    boolean deleteTache(String id);
}
