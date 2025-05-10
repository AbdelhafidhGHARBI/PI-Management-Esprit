    package tn.esprit.user_service.dto;

    import jakarta.validation.constraints.Email;
    import jakarta.validation.constraints.NotBlank;
    import jakarta.validation.constraints.Pattern;
    import jakarta.validation.constraints.Size;

    public record LoginRequest(
            @NotBlank
            @Email
            String email,

            @NotBlank
            @Size(min = 8)
            @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d).+$", message = "Password must contain at least one uppercase letter and one number")
            String password
    ) {}