package tn.esprit.spring.tacheservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.tacheservice.entities.Priorite;
import tn.esprit.spring.tacheservice.entities.Status;
import tn.esprit.spring.tacheservice.entities.Tache;
import tn.esprit.spring.tacheservice.services.tacheService;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/taches")
public class tacheController {
    @Autowired
    private tacheService tacheService;

    // F01: Créer une tâche
    @PostMapping
    public ResponseEntity<Tache> createTache(@RequestBody Tache tache) {
        return ResponseEntity.ok(tacheService.createTache(tache));
    }

    // F02: Lire les détails d'une tâche
    @GetMapping("/{id}")
    public ResponseEntity<Tache> getTacheById(@PathVariable String id) {
        Tache tache = tacheService.getTacheById(id);
        return ResponseEntity.ok(tache);
    }

    // F03: Mettre à jour une tâche
    @PutMapping("/{id}")
    public ResponseEntity<Tache> updateTache(@PathVariable String id, @RequestBody Tache tache) {
        try {
            Tache tacheMiseAJour = tacheService.updateTache(id, tache);
            return ResponseEntity.ok(tacheMiseAJour);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // F04: Supprimer une tâche
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTache(@PathVariable String id) {
        tacheService.deleteTache(id);
        return ResponseEntity.noContent().build();
    }

    // F05: Lister les tâches d'un projet
    @GetMapping("/projet/{projetId}")
    public ResponseEntity<List<Tache>> getTachesByProjet(@PathVariable String projetId) {
        return ResponseEntity.ok(tacheService.getTachesByProjet(projetId));
    }

    // F06: Associer une tâche à une équipe
    @PutMapping("/{id}/equipe/{equipeId}")
    public ResponseEntity<Tache> associerEquipe(@PathVariable String id, @PathVariable String equipeId) {
        try {
            Tache tache = tacheService.associerEquipe(id, equipeId);
            return ResponseEntity.ok(tache);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // F07: Attribuer une tâche à un membre
    @PutMapping("/{id}/assignee/{membreId}")
    public ResponseEntity<Tache> assignerTache(@PathVariable String id, @PathVariable String membreId) {
        try {
            Tache tache = tacheService.assignerTache(id, membreId);
            return ResponseEntity.ok(tache);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // F08: Définir un statut de tâche
    @PutMapping("/{id}/status")
    public ResponseEntity<Tache> updateStatus(@PathVariable String id, @RequestBody Status status) {
        try {
            Tache tache = tacheService.updateStatus(id, status);
            return ResponseEntity.ok(tache);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // F09: Définir des niveaux de priorité
    @PutMapping("/{id}/priorite")
    public ResponseEntity<Tache> updatePriorite(@PathVariable String id, @RequestBody Priorite priorite) {
        try {
            Tache tache = tacheService.updatePriorite(id, priorite);
            return ResponseEntity.ok(tache);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // F10: Gérer les échéances et rappels
    @GetMapping("/echeance/{jours}")
    public ResponseEntity<List<Tache>> getTachesProchesEcheance(@PathVariable int jours) {
        return ResponseEntity.ok(tacheService.getTachesProchesEcheance(jours));
    }

    // F11: Suivi du temps passé
    @PutMapping("/{id}/temps")
    public ResponseEntity<Tache> ajouterTempsPassé(@PathVariable String id, @RequestBody Long minutes) {
        try {
            Tache tache = tacheService.ajouterTempsPassé(id, minutes);
            return ResponseEntity.ok(tache);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // F12: Ajout de commentaires
    @PostMapping("/{id}/commentaires")
    public ResponseEntity<Tache> ajouterCommentaire(@PathVariable String id, @RequestBody Commentaire commentaire) {
        try {
            Tache tache = tacheService.ajouterCommentaire(id, commentaire);
            return ResponseEntity.ok(tache);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // F12: Ajout de pièces jointes
    @PostMapping("/{id}/pieces-jointes")
    public ResponseEntity<Tache> ajouterPieceJointe(@PathVariable String id, @RequestBody PieceJointe pieceJointe) {
        try {
            Tache tache = tacheService.ajouterPieceJointe(id, pieceJointe);
            return ResponseEntity.ok(tache);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // F14 & F15: Tableau de bord et statistiques
    @GetMapping("/stats/projet/{projetId}")
    public ResponseEntity<StatistiqueProjet> getStatistiquesProjet(@PathVariable String projetId) {
        return ResponseEntity.ok(tacheService.getStatistiquesProjet(projetId));
    }

    @GetMapping("/stats/membres/{projetId}")
    public ResponseEntity<Map<String, StatistiqueMembre>> getStatistiquesMembres(@PathVariable String projetId) {
        return ResponseEntity.ok(tacheService.getStatistiquesMembres(projetId));
    }

    // F16: Gestion des dépendances
    @GetMapping("/{id}/peut-demarrer")
    public ResponseEntity<Boolean> peutDemarrerTache(@PathVariable String id) {
        return ResponseEntity.ok(tacheService.peutDemarrerTache(id));
    }

    // F17: Recherche avancée et filtrage
    @GetMapping("/recherche")
    public ResponseEntity<List<Tache>> rechercherTaches(
            @RequestParam(required = false) String projetId,
            @RequestParam(required = false) Status status,
            @RequestParam(required = false) Priorite priorite,
            @RequestParam(required = false) String assigneeId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date deadlineBefore) {

        return ResponseEntity.ok(tacheService.rechercherTaches(
                projetId, status, priorite, assigneeId, deadlineBefore));
    }
}
