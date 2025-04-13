package tn.esprit.ressource_service.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Getter
@Setter
@Document(collection = "classe")
public class classeEntity {
    @Id
    private String id;
    private String name;
    private int capacity;
    //@Enumerated(EnumType.STRING)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Formation formation;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Niveau niveau;
    private String anneeAcademique;
    private String specialite;
}
