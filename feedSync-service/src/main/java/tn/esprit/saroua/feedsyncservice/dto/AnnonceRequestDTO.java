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
public class AnnonceRequestDTO {

    @NotBlank
    private String titre;

    @NotBlank
    private String contenu;

    @NotBlank
    private String type;

    private List<String> destinataires;

    @NotNull
    private Date datePublication;

    private List<String> piecesJointes;

    @NotBlank
    private String auteurId;
}
