package tn.esprit.user_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import tn.esprit.user_service.entities.enums.Role;
import tn.esprit.user_service.entities.enums.Sex;

@Data
public class UserCreateDto {
    @NotBlank(message = "Le prénom est requis")
    private String firstName;

    @NotBlank(message = "Le nom de famille est requis")
    private String lastName;

    @NotBlank(message = "Le CIN est requis")
    @Pattern(regexp = "\\d{8}", message = "Doit contenir exactement 8 chiffres")
    private String cin;

    @NotBlank(message = "L'email est requis")
    @Email(message = "Format d'email invalide")
    private String email;

    @NotBlank(message = "Le numéro de téléphone est requis")
    @Pattern(regexp = "^\\+216\\d{8}$", message = "Numéro tunisien invalide")
    private String phoneNumber;

    @NotNull(message = "Le rôle est requis")
    private Role role;

    @NotNull(message = "Le sexe est requis")
    private Sex sex;
}
