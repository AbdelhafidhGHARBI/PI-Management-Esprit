package tn.esprit.projetservice.dto;

import lombok.Data;
import tn.esprit.projetservice.entities.Status;
import tn.esprit.projetservice.entities.Technologie;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
public class ProjetDTO {
    private String id;
    private String title;
    private String description;
    private Date startDate;
    private Date endDate;
    private Status status;
    private String teamId;
    private List<Technologie> technologies;
    private LocalDateTime creationDate;
    private LocalDateTime lastModifiedDate;

}
