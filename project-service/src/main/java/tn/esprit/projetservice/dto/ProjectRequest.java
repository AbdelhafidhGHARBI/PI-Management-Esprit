package tn.esprit.projetservice.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ProjectRequest {
    @NotBlank
    private String title;

    @NotBlank
    private String description;

    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private String teamId;
    private String[] technologies;

    private String instructorId;
    private List<String> studentIds;
}
