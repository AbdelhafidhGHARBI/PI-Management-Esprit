package tn.esprit.spring.tacheservice.entities;

import lombok.Data;

import java.util.Date;


@Data
public class Commentaire {
    private String id;
    private String contenu;
    private String auteurId;
    private Date dateCreation;
}
