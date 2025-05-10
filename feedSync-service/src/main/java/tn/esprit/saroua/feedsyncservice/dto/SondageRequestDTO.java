package tn.esprit.saroua.feedsyncservice.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SondageRequestDTO {

    @NotBlank
    private String question;

    @NotBlank
    private String typeReponse;

    @NotNull
    private Date dateCreation;

    @NotNull
    private Integer duree;

    @NotBlank
    private String visibilite;

    @NotBlank
    private String auteurId;
}

