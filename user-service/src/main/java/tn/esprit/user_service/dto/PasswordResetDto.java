package tn.esprit.user_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PasswordResetDto {
    @NotBlank
    private String token;

    @NotBlank
    @Size(min = 8)
    private String newPassword;
}