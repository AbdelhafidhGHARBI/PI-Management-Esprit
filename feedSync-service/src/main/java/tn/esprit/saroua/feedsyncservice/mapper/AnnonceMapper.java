package tn.esprit.saroua.feedsyncservice.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.saroua.feedsyncservice.dto.AnnonceRequestDTO;
import tn.esprit.saroua.feedsyncservice.dto.AnnonceResponseDTO;
import tn.esprit.saroua.feedsyncservice.entities.Annonce;

@Component
public class AnnonceMapper {

    public Annonce toEntity(AnnonceRequestDTO dto) {
        return Annonce.builder()
                .titre(dto.getTitre())
                .contenu(dto.getContenu())
                .type(dto.getType())
                .destinataires(dto.getDestinataires())
                .datePublication(dto.getDatePublication())
                .piecesJointes(dto.getPiecesJointes())
                .auteurId(dto.getAuteurId())
                .build();
    }

    public AnnonceResponseDTO toDTO(Annonce entity) {
        return AnnonceResponseDTO.builder()
                .id(entity.getId())
                .titre(entity.getTitre())
                .contenu(entity.getContenu())
                .type(entity.getType())
                .destinataires(entity.getDestinataires())
                .datePublication(entity.getDatePublication())
                .piecesJointes(entity.getPiecesJointes())
                .auteurId(entity.getAuteurId())
                .build();
    }

    public void updateEntityFromDTO(AnnonceRequestDTO dto, Annonce entity) {
        entity.setTitre(dto.getTitre());
        entity.setContenu(dto.getContenu());
        entity.setType(dto.getType());
        entity.setDestinataires(dto.getDestinataires());
        entity.setDatePublication(dto.getDatePublication());
        entity.setPiecesJointes(dto.getPiecesJointes());
        entity.setAuteurId(dto.getAuteurId());
    }
}
