package tn.esprit.projetservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.projetservice.entities.Projet;
import tn.esprit.projetservice.services.projetService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/projet")
public class projetController {
    @Autowired
    private projetService projetService;

    // Create a new project
    @PostMapping
    public ResponseEntity<Projet> createProject(@RequestBody Projet project) {
        return ResponseEntity.ok(projetService.createProject(project));
    }

    // Get all projects
    @GetMapping
    public ResponseEntity<List<Projet>> getAllProjects() {
        return ResponseEntity.ok(projetService.getAllProjects());
    }

    // Get a project by ID
    @GetMapping("/{id}")
    public ResponseEntity<Projet> getProjectById(@PathVariable String id) {
        Optional<Projet> project = projetService.getProjectById(id);
        return project.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update a project
    @PutMapping("/{id}")
    public ResponseEntity<Projet> updateProject(@PathVariable String id, @RequestBody Projet updatedProject) {
        try {
            Projet project = projetService.updateProject(id, updatedProject);
            return ResponseEntity.ok(project);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a project
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable String id) {
        projetService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }
}
