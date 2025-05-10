package tn.esprit.ressourcesservice.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tn.esprit.ressourcesservice.dto.RessourceDto;
import tn.esprit.ressourcesservice.entities.Ressource;

/**
 * Mapper interface for converting between {@link Ressource} entities and {@link RessourceDto} DTOs.
 * <p>
 * This interface uses the <strong>MapStruct</strong> library to automatically generate
 * mapping implementations at compile time. The {@code @Mapper} annotation indicates that
 * this interface is a MapStruct mapper.
 * <p>
 * The {@code componentModel = "spring"} attribute specifies that the generated mapper
 * implementation should be a Spring bean, allowing it to be injected and managed by the
 * Spring framework.
 *
 * @see <a href="https://mapstruct.org/">MapStruct Documentation</a>
 */
@Mapper(componentModel = "spring")
public interface RessourceMapper {

    /**
     * Converts a {@link RessourceDto} to a {@link Ressource} entity.
     * Maps the sallually, you may need to handle salle assignment in service.
     *
     * @param dto the DTO to convert
     * @return the entity
     */
    //@Mapping(target = "nom", ignore = true) // handled manually later
    Ressource mapToEntity(RessourceDto dto);

    /**
     * Converts a {@link Ressource} entity to a {@link RessourceDto}.
     * Extracts salleId from salle if present.
     *
     * @param entity the entity to convert
     * @return the DTO
     */
    //@Mapping(target = "nom", source = "nom")
    RessourceDto mapToDto(Ressource entity);
}
