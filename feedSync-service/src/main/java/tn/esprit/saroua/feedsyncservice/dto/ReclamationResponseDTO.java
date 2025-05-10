package tn.esprit.saroua.feedsyncservice.dto;


import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReclamationResponseDTO {

    private String id;
    private String titre;
    private String description;
    private String categorie;
    private List<String> piecesJointes;
    private String statut;
    private String utilisateurId;
    private String responsableId;
    private Date dateSoumission;
    private Date dateResolution;
}

