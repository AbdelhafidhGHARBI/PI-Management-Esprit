package tn.esprit.ressourcesservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import tn.esprit.ressourcesservice.entities.Classe;
import tn.esprit.ressourcesservice.repositories.ClasseRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClasseServiceImpl implements IClasseService {
    private final ClasseRepository classeRepository;

    @Override
    public Classe createClasse(Classe classe) {
        return classeRepository.save(classe);
    }

    @Override
    public List<Classe> getAllClasses() {
        return classeRepository.findAll();
    }

    @Override
    public Optional<Classe> getClasseById(String id) {
        return classeRepository.findById(id);
    }

    @Override
    public Classe updateClasse(String id, Classe updatedClasse) {
        return classeRepository.findById(id)
                .map(existingClasse -> {
                    existingClasse.setName(updatedClasse.getName());
                    existingClasse.setCapacity(updatedClasse.getCapacity());
                    existingClasse.setFormation(updatedClasse.getFormation());
                    existingClasse.setNiveau(updatedClasse.getNiveau());
                    existingClasse.setAnneeAcademique(updatedClasse.getAnneeAcademique());
                    existingClasse.setSpecialite(updatedClasse.getSpecialite());
                    return classeRepository.save(existingClasse);
                })
                .orElseThrow(() -> new RuntimeException("Classroom not found with id: " + id));
    }

    @Override
    public void deleteClasse(String id) {
classeRepository.deleteById(id);
    }
}
