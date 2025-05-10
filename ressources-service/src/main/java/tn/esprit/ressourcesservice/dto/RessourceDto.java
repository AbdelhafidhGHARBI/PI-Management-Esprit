package tn.esprit.ressourcesservice.dto;
/**
 * Data Transfer Object (DTO) for representing pedagogical resource information.
 *
 * <p>
 * This DTO uses Java 14+ {@code record} to provide an immutable, concise structure
 * for transferring Ressource data between layers (service <-> controller).
 *
 * @param id          The unique identifier of the resource.
 * @param name        The name of the resource.
 * @param type        The type of the resource (e.g., Projector, Computer, Board).
 */
public record RessourceDto(String id,
                           String name,
                           String type) {
    // No additional code needed – records auto-generate getters, toString, etc.
    }

