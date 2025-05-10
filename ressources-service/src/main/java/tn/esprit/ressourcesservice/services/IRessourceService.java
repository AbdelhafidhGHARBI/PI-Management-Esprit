package tn.esprit.ressourcesservice.services;

import tn.esprit.ressourcesservice.entities.Ressource;

import java.util.List;
import java.util.Optional;

public interface IRessourceService {
    Ressource createRessource(Ressource ressource);

    List<Ressource> getAllRessources();

    Optional<Ressource> getRessourceById(String id);

    Ressource updateRessource(String id, Ressource updatedRessource);

    void deleteRessource(String id);
}
