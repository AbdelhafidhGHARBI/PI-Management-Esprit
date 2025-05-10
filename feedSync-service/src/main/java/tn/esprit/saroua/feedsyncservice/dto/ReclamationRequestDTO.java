package tn.esprit.saroua.feedsyncservice.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReclamationRequestDTO {

    @NotBlank
    private String titre;

    @NotBlank
    private String description;

    @NotBlank
    private String categorie;

    private List<String> piecesJointes;

    @NotNull
    private String statut;

    @NotBlank
    private String utilisateurId;

    private String responsableId;

    private Date dateSoumission;

    private Date dateResolution;
}
