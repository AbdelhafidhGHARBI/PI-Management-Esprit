package tn.esprit.ressourcesservice.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document (collection = "ressources")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

public class Ressource {
    @Id
    String id;

    String nom;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    TypeRessource type; // Enumération : PC, PROJECTEUR, TABLEAU...

    String description;
}
