package tn.esprit.user_service.dto;

import lombok.Builder;
import tn.esprit.user_service.entities.enums.Role;

import java.time.Instant;

@Builder
public record AuthResponse(
        String accessToken,
        String refreshToken,
        Instant accessExpiresAt,
        Instant refreshExpiresAt,
        String email,
        Role role
) {}