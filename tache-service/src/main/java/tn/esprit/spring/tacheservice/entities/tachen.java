package tn.esprit.spring.tacheservice.entities;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;



@Data
@Getter
@Setter
@Document(collection = "tache")
public class tachen {
    @Id
    private String id;
    private String titre;
    private String description;

}
