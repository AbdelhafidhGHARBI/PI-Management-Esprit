package tn.esprit.user_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import tn.esprit.user_service.entities.enums.Role;

@Data
public class UserCreateDto {
    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    @Pattern(regexp = "\\d{8}", message = "CIN must be 8 digits")
    private String cin;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Pattern(regexp = "^\\+216\\d{8}$", message = "Invalid Tunisian phone number")
    private String phoneNumber;

    @NotNull
    private Role role;
}
