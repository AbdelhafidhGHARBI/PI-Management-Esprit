package tn.esprit.saroua.feedsyncservice.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "sondages")
public class Sondage {

    @Id
    private String id;

    @NotBlank
    @Field("question")
    private String question;

    @NotBlank
    @Field("typeReponse")
    private String typeReponse; // Exemple : SIMPLE, MULTIPLE, TEXTE

    @NotNull
    @Field("dateCreation")
    private Date dateCreation;

    @NotNull
    @Field("duree")
    private Integer duree; // Durée en jours ou heures

    @NotBlank
    @Field("visibilite")
    private String visibilite; // PUBLIC, PRIVE, EQUIPE

    @NotBlank
    @Field("auteurId")
    private String auteurId;
}
