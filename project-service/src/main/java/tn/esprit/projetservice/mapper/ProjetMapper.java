package tn.esprit.projetservice.mapper;

import tn.esprit.projetservice.dto.ProjetDTO;
import tn.esprit.projetservice.entities.Projet;

public class ProjetMapper {
    public static ProjetDTO toDTO(Projet projet) {
        ProjetDTO dto = new ProjetDTO();
        dto.setId(projet.getId());
        dto.setTitle(projet.getTitle());
        dto.setDescription(projet.getDescription());
        dto.setStartDate(projet.getStartDate());
        dto.setEndDate(projet.getEndDate());
        dto.setStatus(projet.getStatus());
        dto.setTeamId(projet.getTeamId());
        dto.setTechnologies(projet.getTechnologies());
        dto.setCreationDate(projet.getCreationDate());
        dto.setLastModifiedDate(projet.getLastModifiedDate());
        return dto;
    }

    public static Projet toEntity(ProjetDTO dto) {
        Projet projet = new Projet();
        projet.setId(dto.getId());
        projet.setTitle(dto.getTitle());
        projet.setDescription(dto.getDescription());
        projet.setStartDate(dto.getStartDate());
        projet.setEndDate(dto.getEndDate());
        projet.setStatus(dto.getStatus());
        projet.setTeamId(dto.getTeamId());
        projet.setTechnologies(dto.getTechnologies());
        projet.setCreationDate(dto.getCreationDate());
        projet.setLastModifiedDate(dto.getLastModifiedDate());
        return projet;
    }
}
