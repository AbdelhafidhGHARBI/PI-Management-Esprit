package tn.esprit.ressourcesservice.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "salles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Salle {
    @Id
    String id;

    String nom;
    int capacite;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    TypeSalle type; // Enumération : AMPHI, TP, COURS...

    boolean disponible;
}
