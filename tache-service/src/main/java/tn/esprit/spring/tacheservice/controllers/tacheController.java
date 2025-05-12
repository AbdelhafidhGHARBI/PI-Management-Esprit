package tn.esprit.spring.tacheservice.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.tacheservice.dto.TacheDTO;
import tn.esprit.spring.tacheservice.entities.Commentaire;
import tn.esprit.spring.tacheservice.entities.Priorite;
import tn.esprit.spring.tacheservice.entities.Status;
import tn.esprit.spring.tacheservice.entities.Tache;
import tn.esprit.spring.tacheservice.mapper.TacheMapper;
import tn.esprit.spring.tacheservice.services.ITacheService;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/taches")
@RequiredArgsConstructor
public class TacheController {

    private final ITacheService tacheService;
    private final TacheMapper tacheMapper;

    @PostMapping
    public ResponseEntity<TacheDTO> createTache(@RequestBody TacheDTO tacheDTO) {
        TacheDTO createdTache = tacheService.createTache(tacheDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTache);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TacheDTO> getTacheById(@PathVariable String id) {
        return ResponseEntity.ok(tacheService.getTacheById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TacheDTO> updateTache(@PathVariable String id, @RequestBody TacheDTO tacheDTO) {
        return ResponseEntity.ok(tacheService.updateTache(id, tacheDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTache(@PathVariable String id) {
        tacheService.deleteTache(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/projet/{projetId}")
    public ResponseEntity<List<TacheDTO>> getTachesByProjet(@PathVariable String projetId) {
        List<TacheDTO> taches = tacheService.getTachesByProjet(projetId);
        return ResponseEntity.ok(taches.stream()
                .map(tacheMapper::toDTO)
                .collect(Collectors.toList()));
    }


    // F06: Associer une tâche à une équipe
    @PutMapping("/{id}/equipe/{equipeId}")
    public ResponseEntity<TacheDTO> associerEquipe(@PathVariable String id, @PathVariable String equipeId) {
        return ResponseEntity.ok(tacheMapper.toDTO(tacheService.associerEquipe(id, equipeId)));
    }


    // F07: Attribuer une tâche à un membre
    @PutMapping("/{id}/assignee/{membreId}")
    public ResponseEntity<TacheDTO> assignerTache(@PathVariable String id, @PathVariable String membreId) {
        return ResponseEntity.ok(tacheMapper.toDTO(tacheService.assignerTache(id, membreId)));
    }

    // F08: Définir un statut de tâche
    @PutMapping("/{id}/status")
    public ResponseEntity<TacheDTO> updateStatus(@PathVariable String id, @RequestBody Status status) {
        return ResponseEntity.ok(tacheMapper.toDTO(tacheService.updateStatus(id, status)));
    }

    // F09: Définir des niveaux de priorité
    @PutMapping("/{id}/priorite")
    public ResponseEntity<TacheDTO> updatePriorite(@PathVariable String id, @RequestBody Priorite priorite) {
        return ResponseEntity.ok(tacheMapper.toDTO(tacheService.updatePriorite(id, priorite)));
    }

    // F10: Suivi du temps passé
    @PutMapping("/{id}/temps")
    public ResponseEntity<TacheDTO> ajouterTempsPassé(@PathVariable String id, @RequestBody Long minutes) {
        return ResponseEntity.ok(tacheMapper.toDTO(tacheService.ajouterTempsPassé(id, minutes)));
    }

    // F11: Ajout de commentaires
    @PostMapping("/{id}/commentaires")
    public ResponseEntity<TacheDTO> ajouterCommentaire(@PathVariable String id, @RequestBody Commentaire commentaire) {
        return ResponseEntity.ok(tacheMapper.toDTO(tacheService.ajouterCommentaire(id, commentaire)));
}

    // F12: Recherche avancée et filtrage
    @GetMapping("/recherche")
    public ResponseEntity<List<TacheDTO>> rechercherTaches(
            @RequestParam(required = false) String projetId,
            @RequestParam(required = false) Status status,
            @RequestParam(required = false) Priorite priorite,
            @RequestParam(required = false) String assigneeId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date deadlineBefore) {

        List<Tache> resultats = tacheService.rechercherTaches(
                projetId,
                status,
                priorite,
                assigneeId,
                deadlineBefore
        );

        List<TacheDTO> dtos = resultats.stream()
                .map(tacheMapper::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    //    @GetMapping("/stats/projet/{projetId}")
//    public ResponseEntity<StatistiqueProjet> getStatistiquesProjet(@PathVariable String projetId) {
//        return ResponseEntity.ok(tacheService.getStatistiquesProjet(projetId));
//    }  // F14 & F15: Tableau de bord et statistiques
//
//
//    @GetMapping("/stats/membres/{projetId}")
//    public ResponseEntity<Map<String, StatistiqueMembre>> getStatistiquesMembres(@PathVariable String projetId) {
//        return ResponseEntity.ok(tacheService.getStatistiquesMembres(projetId));
//    }

}
