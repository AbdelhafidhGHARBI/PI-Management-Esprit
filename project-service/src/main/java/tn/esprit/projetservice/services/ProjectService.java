package tn.esprit.projetservice.services;

import tn.esprit.projetservice.dto.ProjectRequest;
import tn.esprit.projetservice.dto.ProjectResponse;

import java.util.List;

public interface ProjectService {
    ProjectResponse createProject(ProjectRequest request);
    ProjectResponse updateProject(String id, ProjectRequest request);
    ProjectResponse getProjectById(String id);
    List<ProjectResponse> getAllProjects();
    void deleteProject(String id);
}
