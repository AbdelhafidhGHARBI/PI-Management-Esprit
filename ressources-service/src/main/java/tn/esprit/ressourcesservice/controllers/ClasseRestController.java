package tn.esprit.ressourcesservice.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.ressourcesservice.entities.Classe;
import tn.esprit.ressourcesservice.services.ClasseServiceImpl;
import tn.esprit.ressourcesservice.services.IClasseService;

import java.util.List;

@RestController
@RequestMapping("/classe")
@RequiredArgsConstructor
public class ClasseRestController {

    private final IClasseService classeService;

    @PostMapping("/add")
    public ResponseEntity<Classe> createClasse(@RequestBody Classe classe) {
        return ResponseEntity.ok(classeService.createClasse(classe));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Classe>> getAllClasses() {
        return ResponseEntity.ok(classeService.getAllClasses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Classe> getClasseById(@PathVariable String id) {
        return classeService.getClasseById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Classe> updateClasse(@PathVariable String id, @RequestBody Classe updatedClasse) {
        return ResponseEntity.ok(classeService.updateClasse(id, updatedClasse));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteClasse(@PathVariable String id) {
        classeService.deleteClasse(id);
        return ResponseEntity.noContent().build();
    }
}
