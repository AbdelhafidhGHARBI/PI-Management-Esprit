package tn.esprit.ressourcesservice.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "classes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Classe {
    @Id
    String id;

    String name;
    int capacity;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    Formation formation;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    Niveau niveau;

    String anneeAcademique;
    String specialite;
}
