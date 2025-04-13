package tn.esprit.projetservice.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.projetservice.entities.Projet;
import tn.esprit.projetservice.entities.Status;
import tn.esprit.projetservice.exceptions.ProjetNotFoundException;
import tn.esprit.projetservice.repositories.projetRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j //Fonctionnalités de journalisation avec différents niveaux (info, debug)
@RequiredArgsConstructor
public class projetService {

    private final projetRepository projetRepository;

    @Transactional
    public Projet createProject(Projet projet) {
        log.info("Création d'un nouveau projet: {}", projet.getTitle());

        // Définir la date de création si elle n'est pas spécifiée
        if (projet.getCreationDate() == null) {
            projet.setCreationDate(new Date());
        }

        // Définir le statut par défaut si non spécifié
        if (projet.getStatus() == null) {
            projet.setStatus(changerStatusProjet().PLANIFIER);
        }

        return projetRepository.save(projet);
    }

    public List<Projet> getAllProjects() { log.debug("Récupération de tous les projets");
        return projetRepository.findAll();
    }

    public Projet getProjectById(String id) { log.debug("Récupération du projet avec l'ID: {}", id);
        return projetRepository.findById(id)
                .orElseThrow(() -> new ProjetNotFoundException("Projet non trouvé avec l'ID: " + id));
    }


    @Transactional
    public Projet updateProject(String id, Projet projetMisAJour) {
        log.info("Mise à jour du projet avec l'ID: {}", id);

        return projetRepository.findById(id)
                .map(projetExistant -> {
                    projetExistant.setTitle(projetMisAJour.getTitle());
                    projetExistant.setDescription(projetMisAJour.getDescription());
                    projetExistant.setStartDate(projetMisAJour.getStartDate());
                    projetExistant.setEndDate(projetMisAJour.getEndDate());
                    projetExistant.setStatus(projetMisAJour.getStatus());
                    projetExistant.setTeamId(projetMisAJour.getTeamId());
                    projetExistant.setTechnologies(projetMisAJour.getTechnologies());
                    projetExistant.setLastUpdatedDate(new Date());

                    return projetRepository.save(projetExistant);
                })
                .orElseThrow(() -> new ProjetNotFoundException("Projet non trouvé avec l'ID: " + id));
    }


    @Transactional
    public void deleteProjet(String id) {
        log.info("Suppression du projet avec l'ID: {}", id);
        if (!projetRepository.existsById(id)) {
            throw new ProjetNotFoundException("Projet non trouvé avec l'ID: " + id);
        }
        projetRepository.deleteById(id);
    }


    @Transactional
    public Projet changerStatusProjet(String id, ProjetStatus nouveauStatus) {
        log.info("Changement du statut du projet {} à {}", id, nouveauStatus);

        return projetRepository.findById(id)
                .map(projet -> {
                    projet.setStatus(nouveauStatus);
                    projet.setLastUpdatedDate(new Date());
                    return projetRepository.save(projet);
                })
                .orElseThrow(() -> new ProjetNotFoundException("Projet non trouvé avec l'ID: " + id));
    }


    @Transactional
    public Projet assignerEquipe(String projetId, String equipeId) {
        log.info("Attribution de l'équipe {} au projet {}", equipeId, projetId);

        return projetRepository.findById(projetId)
                .map(projet -> {
                    projet.setTeamId(equipeId);
                    projet.setLastUpdatedDate(new Date());
                    return projetRepository.save(projet);
                })
                .orElseThrow(() -> new ProjetNotFoundException("Projet non trouvé avec l'ID: " + projetId));
    }


    public List<Projet> findProjetsByStatus(ProjetStatus status) {
        log.debug("Recherche des projets avec le statut: {}", status);
        return projetRepository.findByStatus(status);
    }


    public List<Projet> findProjetsByTechnology(String technology) {
        log.debug("Recherche des projets utilisant la technologie: {}", technology);
        return projetRepository.findByTechnologiesContaining(technology);
    }


    public List<Projet> findProjetsByTeam(String teamId) {
        log.debug("Recherche des projets pour l'équipe: {}", teamId);
        return projetRepository.findByTeamId(teamId);
    }
}
