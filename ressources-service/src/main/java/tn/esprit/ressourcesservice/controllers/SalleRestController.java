package tn.esprit.ressourcesservice.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.ressourcesservice.entities.Salle;
import tn.esprit.ressourcesservice.services.ISalleService;

import java.util.List;

@RestController
@RequestMapping("/salle")
@RequiredArgsConstructor
public class SalleRestController {
    private final ISalleService salleService;
    @PostMapping("/add")
    public ResponseEntity<Salle> createSalle(@RequestBody Salle salle) {
        return ResponseEntity.ok(salleService.createSalle(salle));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Salle>> getAllSalles() {
        return ResponseEntity.ok(salleService.getAllSalles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Salle> getSalleById(@PathVariable String id) {
        return salleService.getSalleById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Salle> updateSalle(@PathVariable String id, @RequestBody Salle updatedSalle) {
        return ResponseEntity.ok(salleService.updateSalle(id, updatedSalle));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteSalle(@PathVariable String id) {
        salleService.deleteSalle(id);
        return ResponseEntity.noContent().build();
    }

}
