package tn.esprit.ressource_service.entities;

public enum Niveau {
    PREMIERE_ANNEE("1ère année"),
    DEUXIEME_ANNEE("2ème année"),
    TROISIEME_ANNEE("3ème année"),
    QUATRIEME_ANNEE("4ème année");

    private final String label;

    Niveau(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
