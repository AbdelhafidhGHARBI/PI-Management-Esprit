package tn.esprit.saroua.feedsyncservice.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SondageResponseDTO {

    private String id;
    private String question;
    private String typeReponse;
    private Date dateCreation;
    private Integer duree;
    private String visibilite;
    private String auteurId;
}
