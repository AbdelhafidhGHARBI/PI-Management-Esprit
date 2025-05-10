package tn.esprit.saroua.feedsyncservice.dto;


import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReponseSondageDTO {

    private String id;
    private String sondageId;
    private String utilisateurId;
    private List<String> reponses;
}

