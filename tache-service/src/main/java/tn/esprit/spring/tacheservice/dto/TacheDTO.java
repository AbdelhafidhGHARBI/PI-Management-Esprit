package tn.esprit.spring.tacheservice.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import tn.esprit.spring.tacheservice.entities.Commentaire;
import tn.esprit.spring.tacheservice.entities.PieceJointe;
import tn.esprit.spring.tacheservice.entities.Priorite;
import tn.esprit.spring.tacheservice.entities.Status;

import java.util.Date;
import java.util.List;

@Data
public class TacheDTO {

    private String id;
    @NotBlank(message = "Le titre est obligatoire")
    private String titre;
    private String description;
    private String projetId;
    private String equipeId;
    private String assigneeId;
    private Date dateCreation;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date dateCreationDate;

    @FutureOrPresent(message = "La deadline doit être dans le futur")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date deadline;

    private Priorite priorite;
    private Status status;
    private Long tempsPasseMinutes;
    private List<Commentaire> commentaires;
    private List<PieceJointe> piecesJointes;
    private List<String> dependances;

}
