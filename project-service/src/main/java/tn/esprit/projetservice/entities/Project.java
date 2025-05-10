package tn.esprit.projetservice.entities;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Document("projects")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Project {

    @Id
    private String id;

    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;

    private Status status;

    private String teamId;

    private List<Technologie> technologies;

    private String instructorId;
    private List<String> studentIds;
}
