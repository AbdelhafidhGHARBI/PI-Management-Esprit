package tn.esprit.projetservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.projetservice.dto.ProjectRequest;
import tn.esprit.projetservice.dto.ProjectResponse;
import tn.esprit.projetservice.entities.Project;
import tn.esprit.projetservice.exception.ResourceNotFoundException;

import tn.esprit.projetservice.mapper.ProjectMapper;
import tn.esprit.projetservice.repositories.ProjectRepository;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjetServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    private final ProjectMapper projectMapper;

    @Override
    public ProjectResponse createProject(ProjectRequest request) {
        Project project = projectMapper.toEntity(request);
        return projectMapper.toDto(projectRepository.save(project));
    }

    @Override
    public ProjectResponse updateProject(String id, ProjectRequest request) {
        Project existing = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + id));
        Project updated = projectMapper.toEntity(request);
        updated.setId(existing.getId());
        return projectMapper.toDto(projectRepository.save(updated));
    }

    @Override
    public ProjectResponse getProjectById(String id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + id));
        return projectMapper.toDto(project);
    }

    @Override
    public List<ProjectResponse> getAllProjects() {
        return projectRepository.findAll().stream().map(projectMapper::toDto).toList();
    }

    @Override
    public void deleteProject(String id) {
        if (!projectRepository.existsById(id)) {
            throw new ResourceNotFoundException("Project not found with id: " + id);
        }
        projectRepository.deleteById(id);
    }
}