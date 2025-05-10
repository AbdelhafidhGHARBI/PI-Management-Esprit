package tn.esprit.projetservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;
import tn.esprit.projetservice.dto.ProjectRequest;
import tn.esprit.projetservice.dto.ProjectResponse;
import tn.esprit.projetservice.entities.Project;
import tn.esprit.projetservice.entities.Status;
import tn.esprit.projetservice.entities.Technologie;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProjectMapper {

    public Project toEntity(ProjectRequest request) {
        return Project.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .status(convertToStatus(request.getStatus()))
                .teamId(request.getTeamId())
                .technologies(toTechnologieList(request.getTechnologies()))
                .instructorId(request.getInstructorId())
                .studentIds(request.getStudentIds())
                .build();
    }

    public ProjectResponse toDto(Project project) {
        return ProjectResponse.builder()
                .id(project.getId())
                .title(project.getTitle())
                .description(project.getDescription())
                .startDate(project.getStartDate())
                .endDate(project.getEndDate())
                .status(project.getStatus() != null ? project.getStatus().name() : null)
                .teamId(project.getTeamId())
                .technologies(toTechNames(project.getTechnologies()))
                .instructorId(project.getInstructorId())
                .studentIds(project.getStudentIds())
                .build();
    }

    private Status convertToStatus(String status) {
        try {
            return Status.valueOf(status);
        } catch (Exception e) {
            return Status.PLANIFIER;
        }
    }

    private List<Technologie> toTechnologieList(String[] techNames) {
        if (techNames == null) return List.of();
        return Arrays.stream(techNames)
                .map(name -> Technologie.builder().name(name).build())
                .collect(Collectors.toList());
    }

    private String[] toTechNames(List<Technologie> techObjects) {
        if (techObjects == null) return new String[0];
        return techObjects.stream()
                .map(Technologie::getName)
                .toArray(String[]::new);
    }
}
