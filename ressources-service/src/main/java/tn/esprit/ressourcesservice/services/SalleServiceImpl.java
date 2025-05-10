package tn.esprit.ressourcesservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.ressourcesservice.entities.Salle;
import tn.esprit.ressourcesservice.repositories.SalleRepository;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class SalleServiceImpl implements ISalleService {
    private final SalleRepository salleRepository;

    @Override
    public Salle createSalle(Salle salle) {
        return salleRepository.save(salle);
    }

    @Override
    public List<Salle> getAllSalles() {
        return salleRepository.findAll();
    }

    @Override
    public Optional<Salle> getSalleById(String id) {
        return salleRepository.findById(id);
    }

    @Override
    public Salle updateSalle(String id, Salle updatedSalle) {
        return salleRepository.findById(id)
                .map(existingSalle -> {
                    existingSalle.setNom(updatedSalle.getNom());
                    existingSalle.setCapacite(updatedSalle.getCapacite());
                    existingSalle.setType(updatedSalle.getType());
                    existingSalle.setDisponible(updatedSalle.isDisponible());
                    return salleRepository.save(existingSalle);
                })
                .orElseThrow(() -> new RuntimeException("Salle not found with id: " + id));
    }

    @Override
    public void deleteSalle(String id) {
        salleRepository.deleteById(id);
    }
}
