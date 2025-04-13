package tn.esprit.spring.modelecommunservice.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * Représente les statistiques d'un membre concernant ses tâches assignées.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatistiqueMembre {
    private String membreId;
    private int tachesAssignees;
    private int tachesTerminees;
    private Map<String, Integer> tachesParProjet = new HashMap<>(); // Nombre de tâches par projet

    /**
     * Calcule le taux de complétion des tâches d'un membre (pourcentage).
     * @return Le pourcentage de tâches terminées sur le total des tâches assignées
     */
    public double getTauxCompletion() {
        return tachesAssignees > 0 ? ((double) tachesTerminees / tachesAssignees) * 100 : 0;
    }

    /**
     * Ajoute une tâche au compteur pour un projet spécifique.
     * @param projetId L'ID du projet
     */
    public void incrementerTacheProjet(String projetId) {
        int nbTaches = tachesParProjet.getOrDefault(projetId, 0);
        tachesParProjet.put(projetId, nbTaches + 1);
    }

    /**
     * Obtient le nombre de tâches en cours (non terminées).
     * @return Le nombre de tâches en cours
     */
    public int getTachesEnCours() {
        return tachesAssignees - tachesTerminees;
    }
}
