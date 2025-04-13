package tn.esprit.spring.tacheservice.entities;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@Getter
@Setter
@Document(collection = "taches")
public class Tache {
    @Id
    private String id;
    private String titre;
    private String description;
    private String projetId; // Référence au projet
    private String equipeId; // Référence à l'équipe
    private String assigneeId; // Membre d'équipe assigné
    private Date dateCreation;
    private Date deadline;
    private Priorite priorite;
    private Status status;
    private Long tempsPasseMinutes; // Pour le suivi du temps (F11)
    private List<Commentaire> commentaires; // Structure améliorée pour les commentaires (F12)
    private List<PieceJointe> piecesJointes; // Structure améliorée pour les pièces jointes (F12)
    private List<String> dependances; // IDs des tâches dépendantes (F16)
}

