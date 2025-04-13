package tn.esprit.projetservice.entities;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@Getter
@Setter
@Document(collection = "projects")
public class Projet {
    @Id
    private String id;
    private String title;
    private String description;
    private Date startDate;
    private Date endDate;
    private Status status;
    private String teamId;
    private List<Technologie> technologies;
    private LocalDateTime lastModifiedDate;

    @CreatedDate
    @Field("creation_date")
    private LocalDateTime creationDate;

    @LastModifiedDate
    @Field("last_modified_date")
    private LocalDateTime lastModifiedDateTime;

}

