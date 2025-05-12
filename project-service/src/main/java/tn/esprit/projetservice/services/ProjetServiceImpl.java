package tn.esprit.projetservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.projetservice.entities.Projet;
import tn.esprit.projetservice.repositories.projetRepository;
import java.util.List;
import java.util.Optional;

@Service
public class ProjetServiceImpl implements IProjetService {

    @Autowired
    private projetRepository projetRepository;

    @Override
    public Projet createProject(Projet project) {
        return projetRepository.save(project);
    }

    @Override
    public List<Projet> getAllProjects() {
        return projetRepository.findAll();
    }

    @Override
    public Optional<Projet> getProjectById(String id) {
        return projetRepository.findById(id);
    }

    @Override
    public Projet updateProject(String id, Projet updatedProject) {
        updatedProject.setId(id); // S'assurer que l'ID est conservé
        return projetRepository.save(updatedProject);
    }

    @Override
    public void deleteProject(String id) {
        projetRepository.deleteById(id);
    }
}