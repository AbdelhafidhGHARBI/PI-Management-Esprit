package tn.esprit.spring.modelecommunservice.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Représente les statistiques globales d'un projet.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatistiqueProjet {
    private String projetId;
    private int totalTaches;
    private int tachesTerminees;
    private int tachesEnCours;
    private int tachesAFaire;
    private int tachesBloquees;

    /**
     * Calcule le pourcentage d'achèvement du projet.
     * @return Le pourcentage des tâches terminées sur le total des tâches
     */
    public double getPourcentageAchevement() {
        return totalTaches > 0 ? ((double) tachesTerminees / totalTaches) * 100 : 0;
    }

    /**
     * Calcule le pourcentage de tâches bloquées.
     * @return Le pourcentage des tâches bloquées sur le total des tâches
     */
    public double getPourcentageTachesBloquees() {
        return totalTaches > 0 ? ((double) tachesBloquees / totalTaches) * 100 : 0;
    }

    /**
     * Détermine si le projet présente des risques basés sur le pourcentage de tâches bloquées.
     * @return true si plus de 20% des tâches sont bloquées
     */
    public boolean estEnRisque() {
        return getPourcentageTachesBloquees() > 20;
    }

    /**
     * Calcule le pourcentage de tâches en cours.
     * @return Le pourcentage des tâches en cours sur le total des tâches
     */
    public double getPourcentageTachesEnCours() {
        return totalTaches > 0 ? ((double) tachesEnCours / totalTaches) * 100 : 0;
    }
}

