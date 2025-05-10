package tn.esprit.projetservice.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Technologie {
    private String name;
    private String image;
}
