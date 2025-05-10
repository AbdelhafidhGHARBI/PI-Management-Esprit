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
@Document(collection = "annonces")
public class Annonce {

    @Id
    private String id;

    @NotBlank
    @Field("titre")
    private String titre;

    @NotBlank
    @Field("contenu")
    private String contenu;

    @NotBlank
    @Field("type")
    private String type;

    @Field("destinataires")
    private List<String> destinataires;

    @NotNull
    @Field("datePublication")
    private Date datePublication;

    @Field("piecesJointes")
    private List<String> piecesJointes;

    @NotBlank
    @Field("auteurId")
    private String auteurId;
}
