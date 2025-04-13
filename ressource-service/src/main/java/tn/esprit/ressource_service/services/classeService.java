package tn.esprit.ressource_service.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.ressource_service.repositories.classeeRepository;
import tn.esprit.ressource_service.entities.classeEntity;

import java.util.List;
import java.util.Optional;

@Service
public class classeService {
    @Autowired
    private classeeRepository classeRepository;

    // Create a new classroom
    public classeEntity createClasse(classeEntity classe) {
        return classeRepository.save(classe);
    }

    // Get all classrooms
    public List<classeEntity> getAllClasses() {
        return classeRepository.findAll();
    }

    // Get a classroom by ID
    public Optional<classeEntity> getClasseById(String id) {
        return classeRepository.findById(id);
    }

    // Update a classroom
    public classeEntity updateClasse(String id, classeEntity updatedClasse) {
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

    // Delete a classroom
    public void deleteClasse(String id) {
        classeRepository.deleteById(id);
    }
}
