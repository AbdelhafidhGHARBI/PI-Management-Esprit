package tn.esprit.ressource_service.entities;

public enum Formation {
    GENIE_INFORMATIQUE("Génie Informatique"),
    GENIE_MECATRONIQUE("Génie Mécatronique"),
    GENIE_ELECTRIQUE("Génie Electrique"),
    GENIE_CIVIL("Génie Civil"),
    GENIE_INDUSTRIEL("Génie Industriel");

    private final String label;

    Formation(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
