package tn.esprit.saroua.feedsyncservice.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import jakarta.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "reponses_sondages")
public class ReponseSondage {

    @Id
    private String id;

    @NotBlank
    @Field("sondageId")
    private String sondageId;

    @NotBlank
    @Field("utilisateurId")
    private String utilisateurId;

    @Field("reponses")
    private List<String> reponses;
}
