package tn.esprit.spring.tacheservice.dto;

import lombok.Data;
import tn.esprit.spring.tacheservice.entities.Commentaire;
import tn.esprit.spring.tacheservice.entities.PieceJointe;
import tn.esprit.spring.tacheservice.entities.Priorite;
import tn.esprit.spring.tacheservice.entities.Status;

import java.util.Date;
import java.util.List;

@Data
public class TacheDTO {

    private String id;
    private String titre;
    private String description;
    private String projetId;
    private String equipeId;
    private String assigneeId;
    private Date dateCreation;
    private Date deadline;
    private Priorite priorite;
    private Status status;
    private Long tempsPasseMinutes;
    private List<Commentaire> commentaires;
    private List<PieceJointe> piecesJointes;
    private List<String> dependances;

}
