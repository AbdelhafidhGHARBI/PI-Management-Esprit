package tn.esprit.ressourcesservice.services;

import tn.esprit.ressourcesservice.entities.Salle;

import java.util.List;
import java.util.Optional;

public interface ISalleService {
    Salle createSalle(Salle salle);

    List<Salle> getAllSalles();

    Optional<Salle> getSalleById(String id);

    Salle updateSalle(String id, Salle updatedSalle);

    void deleteSalle(String id);
}
