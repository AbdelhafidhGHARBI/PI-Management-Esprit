package tn.esprit.saroua.feedsyncservice.dto;


import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnnonceResponseDTO {

    private String id;
    private String titre;
    private String contenu;
    private String type;
    private List<String> destinataires;
    private Date datePublication;
    private List<String> piecesJointes;
    private String auteurId;
}
