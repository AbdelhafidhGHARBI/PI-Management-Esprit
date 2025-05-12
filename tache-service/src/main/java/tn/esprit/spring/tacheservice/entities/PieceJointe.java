package tn.esprit.spring.tacheservice.entities;

import lombok.Data;

import java.util.Date;


@Data
public class PieceJointe {
    private String id;
    private String nom;
    private String type;
    private String url;
    private Date dateAjout;
    private String ajoutePar;
}
