package tn.esprit.ressourcesservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.ressourcesservice.entities.Ressource;
import tn.esprit.ressourcesservice.repositories.RessourceRepository;
import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class RessourceServiceImpl implements IRessourceService{
    private final RessourceRepository ressourceRepository;


    @Override
    public Ressource createRessource(Ressource ressource) {
        return ressourceRepository.save(ressource);
    }

    @Override
    public List<Ressource> getAllRessources() {
        return ressourceRepository.findAll();
    }

    @Override
    public Optional<Ressource> getRessourceById(String id) {
        return ressourceRepository.findById(id);
    }

    @Override
    public Ressource updateRessource(String id, Ressource updatedRessource) {
        Optional<Ressource> existingOpt = ressourceRepository.findById(id);
        if (existingOpt.isPresent()) {
            Ressource existing = existingOpt.get();
            existing.setId(updatedRessource.getId());
            existing.setType(updatedRessource.getType());
            existing.setDescription(updatedRessource.getDescription());
            return ressourceRepository.save(existing);
        }
        return null;
    }

    @Override
    public void deleteRessource(String id) {
ressourceRepository.deleteById(id);
    }
}
