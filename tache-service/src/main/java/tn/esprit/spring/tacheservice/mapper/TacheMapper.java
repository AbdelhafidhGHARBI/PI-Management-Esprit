package tn.esprit.spring.tacheservice.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.spring.tacheservice.dto.TacheDTO;
import tn.esprit.spring.tacheservice.entities.Tache;

@Component
public class TacheMapper {

    public TacheDTO toDTO(Tache tache) {
        if (tache == null) return null;

        TacheDTO dto = new TacheDTO();
        dto.setId(tache.getId());
        dto.setTitre(tache.getTitre());
        dto.setDescription(tache.getDescription());
        dto.setProjetId(tache.getProjetId());
        dto.setEquipeId(tache.getEquipeId());
        dto.setAssigneeId(tache.getAssigneeId());
        dto.setDateCreation(tache.getDateCreation());
        dto.setDeadline(tache.getDeadline());
        dto.setPriorite(tache.getPriorite());
        dto.setStatus(tache.getStatus());
        dto.setTempsPasseMinutes(tache.getTempsPasseMinutes());
        dto.setCommentaires(tache.getCommentaires());
        dto.setPiecesJointes(tache.getPiecesJointes());
        dto.setDependances(tache.getDependances());

        return dto;
    }

    // DTO vers entité
    public Tache toEntity(TacheDTO dto) {
        if (dto == null) return null;

        Tache tache = new Tache();
        tache.setId(dto.getId());
        tache.setTitre(dto.getTitre());
        tache.setDescription(dto.getDescription());
        tache.setProjetId(dto.getProjetId());
        tache.setEquipeId(dto.getEquipeId());
        tache.setAssigneeId(dto.getAssigneeId());
        tache.setDateCreation(dto.getDateCreation());
        tache.setDeadline(dto.getDeadline());
        tache.setPriorite(dto.getPriorite());
        tache.setStatus(dto.getStatus());
        tache.setTempsPasseMinutes(dto.getTempsPasseMinutes());
        tache.setCommentaires(dto.getCommentaires());
        tache.setPiecesJointes(dto.getPiecesJointes());
        tache.setDependances(dto.getDependances());

        return tache;
    }
}
