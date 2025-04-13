package tn.esprit.ressource_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tn.esprit.ressource_service.services.classeService;
import tn.esprit.ressource_service.entities.classeEntity;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/classe")
public class classeController {
    @Autowired
    private classeService classeService;

    // Create a new classroom
    @PostMapping
    public ResponseEntity<classeEntity> createClasse(@RequestBody classeEntity classe) {
        return ResponseEntity.ok(classeService.createClasse(classe));
    }

    // Get all classrooms
    @GetMapping
    public ResponseEntity<List<classeEntity>> getAllClasses() {
        return ResponseEntity.ok(classeService.getAllClasses());
    }

    // Get a classroom by ID
    @GetMapping("/{id}")
    public ResponseEntity<classeEntity> getClasseById(@PathVariable String id) {
        Optional<classeEntity> classe = classeService.getClasseById(id);
        return classe.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update a classroom
    @PutMapping("/{id}")
    public ResponseEntity<classeEntity> updateClasse(@PathVariable String id, @RequestBody classeEntity updatedClasse) {
        try {
            classeEntity classe = classeService.updateClasse(id, updatedClasse);
            return ResponseEntity.ok(classe);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a classroom
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClasse(@PathVariable String id) {
        classeService.deleteClasse(id);
        return ResponseEntity.noContent().build();
    }
}
