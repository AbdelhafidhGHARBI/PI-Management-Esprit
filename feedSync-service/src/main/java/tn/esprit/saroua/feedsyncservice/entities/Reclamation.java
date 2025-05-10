package tn.esprit.saroua.feedsyncservice.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "reclamations")
public class Reclamation {

    @Id
    private String id;

    @NotBlank
    @Field("titre")
    private String titre;

    @NotBlank
    @Field("description")
    private String description;

    @NotBlank
    @Field("categorie")
    private String categorie;

    @Field("piecesJointes")
    private List<String> piecesJointes;

    @NotNull
    @Field("statut")
    private StatutReclamation statut;

    @NotBlank
    @Field("utilisateurId")
    private String utilisateurId;

    @Field("responsableId")
    private String responsableId;

    @Field("dateSoumission")
    private Date dateSoumission;

    @Field("dateResolution")
    private Date dateResolution;
}

