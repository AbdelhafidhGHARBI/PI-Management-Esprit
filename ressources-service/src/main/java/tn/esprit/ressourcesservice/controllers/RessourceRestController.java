package tn.esprit.ressourcesservice.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.ressourcesservice.entities.Ressource;
import tn.esprit.ressourcesservice.services.IRessourceService;

import java.util.List;

@RestController
@RequestMapping("/ressource")
@RequiredArgsConstructor
public class RessourceRestController {

    private final IRessourceService ressourceService;

    @PostMapping("/add")
    public ResponseEntity<Ressource> createRessource(@RequestBody Ressource ressource) {
        return ResponseEntity.ok(ressourceService.createRessource(ressource));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Ressource>> getAllRessources() {
        return ResponseEntity.ok(ressourceService.getAllRessources());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ressource> getRessourceById(@PathVariable String id) {
        return ressourceService.getRessourceById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Ressource> updateRessource(@PathVariable String id, @RequestBody Ressource updatedRessource) {
        return ResponseEntity.ok(ressourceService.updateRessource(id, updatedRessource));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteRessource(@PathVariable String id) {
        ressourceService.deleteRessource(id);
        return ResponseEntity.noContent().build();
    }

}
