package tn.esprit.spring.tacheservice.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tn.esprit.spring.tacheservice.entities.*;
import tn.esprit.spring.tacheservice.repositories.tacheRepository;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class tacheService {
    @Autowired
    private tacheRepository tacheRepository;

    // Pour les notifications
    @Autowired
    private NotificationService notificationService;

    // F01: Créer une tâche
    public Tache createTache(Tache tache) {
        tache.setDateCreation(new Date());
        if (tache.getStatus() == null) {
            tache.setStatus(Status.A_FAIRE);
        }
        Tache nouvelleTache = tacheRepository.save(tache);

        // Notification de création (F18)
        if (tache.getAssigneeId() != null) {
            notificationService.envoyerNotification(tache.getAssigneeId(),
                    "Nouvelle tâche assignée: " + tache.getTitre());
        }

        return nouvelleTache;
    }

    // F02: Lire les détails d'une tâche
    public Tache getTacheById(String id) {
        return tacheRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tâche non trouvée avec l'id: " + id));
    }

    // F03: Mettre à jour une tâche
    public Tache updateTache(String id, Tache tacheModifiee) {
        return tacheRepository.findById(id)
                .map(tache -> {
                    // Sauvegarde de l'ancien assigné pour la notification
                    String ancienAssigneeId = tache.getAssigneeId();

                    // Mise à jour des champs
                    tache.setTitre(tacheModifiee.getTitre());
                    tache.setDescription(tacheModifiee.getDescription());
                    tache.setProjetId(tacheModifiee.getProjetId());
                    tache.setEquipeId(tacheModifiee.getEquipeId());
                    tache.setAssigneeId(tacheModifiee.getAssigneeId());
                    tache.setDeadline(tacheModifiee.getDeadline());
                    tache.setPriorite(tacheModifiee.getPriorite());
                    tache.setStatus(tacheModifiee.getStatus());
                    tache.setDependances(tacheModifiee.getDependances());

                    Tache tacheMiseAJour = tacheRepository.save(tache);

                    // Notification si l'assigné a changé (F18)
                    if (tacheModifiee.getAssigneeId() != null &&
                            !tacheModifiee.getAssigneeId().equals(ancienAssigneeId)) {
                        notificationService.envoyerNotification(tacheModifiee.getAssigneeId(),
                                "Tâche assignée: " + tache.getTitre());
                    }

                    // Notification si le statut change (F18)
                    if (tacheModifiee.getStatus() != null && !tacheModifiee.getStatus().equals(tache.getStatus())) {
                        notificationService.envoyerNotificationProjet(tache.getProjetId(),
                                "Statut de tâche modifié: " + tache.getTitre() + " est maintenant " + tache.getStatus());
                    }

                    return tacheMiseAJour;
                })
                .orElseThrow(() -> new RuntimeException("Tâche non trouvée avec l'id: " + id));
    }

    // F04: Supprimer une tâche
    public void deleteTache(String id) {
        tacheRepository.deleteById(id);
    }

    // F05: Lister les tâches d'un projet
    public List<Tache> getTachesByProjet(String projetId) {
        return tacheRepository.findByProjetId(projetId);
    }

    // F06: Associer une tâche à une équipe
    public Tache associerEquipe(String tacheId, String equipeId) {
        return tacheRepository.findById(tacheId)
                .map(tache -> {
                    tache.setEquipeId(equipeId);
                    return tacheRepository.save(tache);
                })
                .orElseThrow(() -> new RuntimeException("Tâche non trouvée avec l'id: " + tacheId));
    }

    // F07: Attribuer une tâche à un membre
    public Tache assignerTache(String tacheId, String membreId) {
        return tacheRepository.findById(tacheId)
                .map(tache -> {
                    tache.setAssigneeId(membreId);
                    Tache tacheMiseAJour = tacheRepository.save(tache);

                    // Notification (F18)
                    notificationService.envoyerNotification(membreId,
                            "Nouvelle tâche assignée: " + tache.getTitre());

                    return tacheMiseAJour;
                })
                .orElseThrow(() -> new RuntimeException("Tâche non trouvée avec l'id: " + tacheId));
    }

    // F08: Définir un statut de tâche
    public Tache updateStatus(String tacheId, Status nouveauStatus) {
        return tacheRepository.findById(tacheId)
                .map(tache -> {
                    Status ancienStatus = tache.getStatus();
                    tache.setStatus(nouveauStatus);
                    Tache tacheMiseAJour = tacheRepository.save(tache);

                    // Notification (F18)
                    if (tache.getAssigneeId() != null) {
                        notificationService.envoyerNotification(tache.getAssigneeId(),
                                "Statut de tâche modifié: " + tache.getTitre() + " est maintenant " + nouveauStatus);
                    }

                    return tacheMiseAJour;
                })
                .orElseThrow(() -> new RuntimeException("Tâche non trouvée avec l'id: " + tacheId));
    }

    // F09: Définir des niveaux de priorité
    public Tache updatePriorite(String tacheId, Priorite nouvellePriorite) {
        return tacheRepository.findById(tacheId)
                .map(tache -> {
                    tache.setPriorite(nouvellePriorite);
                    Tache tacheMiseAJour = tacheRepository.save(tache);

                    // Notification (F18)
                    if (tache.getAssigneeId() != null) {
                        notificationService.envoyerNotification(tache.getAssigneeId(),
                                "Priorité de tâche modifiée: " + tache.getTitre() + " est maintenant " + nouvellePriorite);
                    }

                    return tacheMiseAJour;
                })
                .orElseThrow(() -> new RuntimeException("Tâche non trouvée avec l'id: " + tacheId));
    }

    // F10: Gérer les échéances et rappels
    public List<Tache> getTachesProchesEcheance(int joursAvantEcheance) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, joursAvantEcheance);
        Date dateEcheance = cal.getTime();

        return tacheRepository.findByDeadlineBefore(dateEcheance);
    }

    // Envoi des rappels automatiques
    @Scheduled(cron = "0 0 8 * * ?") // Tous les jours à 8h00
    public void envoyerRappelsEcheances() {
        List<Tache> tachesProchesEcheance = getTachesProchesEcheance(2); // 2 jours avant échéance

        for (Tache tache : tachesProchesEcheance) {
            if (tache.getAssigneeId() != null) {
                notificationService.envoyerNotification(tache.getAssigneeId(),
                        "Rappel: la tâche '" + tache.getTitre() + "' arrive à échéance dans moins de 2 jours");
            }
        }
    }

    // F11: Suivi du temps passé
    public Tache ajouterTempsPassé(String tacheId, Long minutesPassees) {
        return tacheRepository.findById(tacheId)
                .map(tache -> {
                    Long tempsCourant = tache.getTempsPasseMinutes() != null ? tache.getTempsPasseMinutes() : 0L;
                    tache.setTempsPasseMinutes(tempsCourant + minutesPassees);
                    return tacheRepository.save(tache);
                })
                .orElseThrow(() -> new RuntimeException("Tâche non trouvée avec l'id: " + tacheId));
    }

    // F12: Ajout de commentaires
    public Tache ajouterCommentaire(String tacheId, Commentaire commentaire) {
        return tacheRepository.findById(tacheId)
                .map(tache -> {
                    List<Commentaire> commentaires = tache.getCommentaires();
                    commentaire.setId(java.util.UUID.randomUUID().toString());
                    commentaire.setDateCreation(new Date());

                    if (commentaires == null) {
                        tache.setCommentaires(List.of(commentaire));
                    } else {
                        commentaires.add(commentaire);
                    }

                    Tache tacheMiseAJour = tacheRepository.save(tache);

                    // Notification (F18)
                    if (tache.getAssigneeId() != null && !tache.getAssigneeId().equals(commentaire.getAuteurId())) {
                        notificationService.envoyerNotification(tache.getAssigneeId(),
                                "Nouveau commentaire sur la tâche: " + tache.getTitre());
                    }

                    return tacheMiseAJour;
                })
                .orElseThrow(() -> new RuntimeException("Tâche non trouvée avec l'id: " + tacheId));
    }

    // F12: Ajout de pièces jointes
    public Tache ajouterPieceJointe(String tacheId, PieceJointe pieceJointe) {
        return tacheRepository.findById(tacheId)
                .map(tache -> {
                    List<PieceJointe> piecesJointes = tache.getPiecesJointes();
                    pieceJointe.setId(java.util.UUID.randomUUID().toString());
                    pieceJointe.setDateAjout(new Date());

                    if (piecesJointes == null) {
                        tache.setPiecesJointes(List.of(pieceJointe));
                    } else {
                        piecesJointes.add(pieceJointe);
                    }

                    return tacheRepository.save(tache);
                })
                .orElseThrow(() -> new RuntimeException("Tâche non trouvée avec l'id: " + tacheId));
    }

    // F14 & F15: Tableau de bord et statistiques
    public StatistiqueProjet getStatistiquesProjet(String projetId) {
        List<Tache> tachesProjet = tacheRepository.findByProjetId(projetId);

        StatistiqueProjet stats = new StatistiqueProjet();
        stats.setProjetId(projetId);
        stats.setTotalTaches(tachesProjet.size());
        stats.setTachesTerminees((int) tachesProjet.stream()
                .filter(tache -> tache.getStatus() == Status.TERMINEE)
                .count());
        stats.setTachesEnCours((int) tachesProjet.stream()
                .filter(tache -> tache.getStatus() == Status.EN_COURS)
                .count());
        stats.setTachesAFaire((int) tachesProjet.stream()
                .filter(tache -> tache.getStatus() == Status.A_FAIRE)
                .count());
        stats.setTachesBloquees((int) tachesProjet.stream()
                .filter(tache -> tache.getStatus() == Status.BLOQUEE)
                .count());

        return stats;
    }

    public Map<String, statistiquememb> getStatistiquesMembres(String projetId) {
        List<Tache> tachesProjet = tacheRepository.findByProjetId(projetId);
        Map<String, StatistiqueMembre> statsMembres = new HashMap<>();

        for (Tache tache : tachesProjet) {
            if (tache.getAssigneeId() != null) {
                String membreId = tache.getAssigneeId();
                StatistiqueMembre statsMembre = statsMembres.getOrDefault(membreId, new StatistiqueMembre());
                statsMembre.setMembreId(membreId);
                statsMembre.setTachesAssignees(statsMembre.getTachesAssignees() + 1);

                if (tache.getStatus() == Status.TERMINEE) {
                    statsMembre.setTachesTerminees(statsMembre.getTachesTerminees() + 1);
                }

                Map<String, Integer> tachesParProjet = statsMembre.getTachesParProjet();
                if (tachesParProjet == null) {
                    tachesParProjet = new HashMap<>();
                    statsMembre.setTachesParProjet(tachesParProjet);
                }

                int nbTaches = tachesParProjet.getOrDefault(tache.getProjetId(), 0);
                tachesParProjet.put(tache.getProjetId(), nbTaches + 1);

                statsMembres.put(membreId, statsMembre);
            }
        }

        return statsMembres;
    }

    // F16: Gestion des dépendances
    public boolean peutDemarrerTache(String tacheId) {
        Optional<Tache> tacheOpt = tacheRepository.findById(tacheId);
        if (tacheOpt.isPresent()) {
            Tache tache = tacheOpt.get();
            if (tache.getDependances() == null || tache.getDependances().isEmpty()) {
                return true;
            }

            List<Tache> tachesDependantes = tacheRepository.findAllById(tache.getDependances());
            return tachesDependantes.stream()
                    .allMatch(dep -> dep.getStatus() == Status.TERMINEE);
        }
        return false;
    }

    // F17: Recherche avancée et filtrage
    public List<Tache> rechercherTaches(String projetId, Status status, Priorite priorite,
                                        String assigneeId, Date deadlineBefore) {
        // Implémentation simplifiée - à améliorer pour gérer toutes les combinaisons
        if (projetId != null && status != null && priorite != null) {
            return tacheRepository.findByProjetIdAndStatusAndPriorite(projetId, status, priorite);
        } else if (projetId != null && status != null) {
            return tacheRepository.findByProjetIdAndStatus(projetId, status);
        } else if (assigneeId != null && deadlineBefore != null) {
            return tacheRepository.findByAssigneeIdAndDeadlineBefore(assigneeId, deadlineBefore);
        } else if (projetId != null) {
            return tacheRepository.findByProjetId(projetId);
        } else if (status != null) {
            return tacheRepository.findByStatus(status);
        } else if (assigneeId != null) {
            return tacheRepository.findByAssigneeId(assigneeId);
        } else {
            return tacheRepository.findAll();
        }
    }

    // F08: Lister les tâches d'un membre
    public List<Tache> getTachesParAssignee(String assigneeId) {
        return tacheRepository.findByAssigneeId(assigneeId);
    }

    // F09: Lister les tâches en retard
    public List<Tache> getTachesEnRetard() {
        Date now = new Date();
        return tacheRepository.findAll().stream()
                .filter(t -> t.getDeadline() != null && now.after(t.getDeadline()) && t.getStatus() != Status.TERMINEE)
                .collect(Collectors.toList());
    }

}