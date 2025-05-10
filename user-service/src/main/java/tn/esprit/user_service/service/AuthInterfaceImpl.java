package tn.esprit.user_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tn.esprit.user_service.dto.AuthResponse;
import tn.esprit.user_service.dto.LoginRequest;
import tn.esprit.user_service.entities.DeviceInfo;
import tn.esprit.user_service.entities.User;
import tn.esprit.user_service.entities.enums.AccountStatus;
import tn.esprit.user_service.repositories.UserRepository;
import tn.esprit.user_service.service.servicesInterfaces.AuthService;

import java.time.Instant;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthInterfaceImpl implements AuthService {

    private static final int MAX_ATTEMPTS = 5;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    public AuthResponse authenticate(LoginRequest request, DeviceInfo device) {
        try {
            User user = userRepository.findByEmail(request.email())
                    .orElseThrow(() -> new BadCredentialsException("Invalid credentialss"));            System.out.println(user);
            checkAccountStatus(user);

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            handleFailedLoginAttempt(user);
            throw new BadCredentialsException("Invalid credentials for password ! Please try again");
        }

        handleSuccessfulLogin(user, device);

        return buildAuthResponse(user);
        } catch (UsernameNotFoundException e) {
            throw new BadCredentialsException("Invalid credentials");
        }
    }

    private void checkAccountStatus(User user) {
        if (!user.isEnabled()) {
            throw new DisabledException("Account is not active");
        }
        if (user.getAccountStatus() == AccountStatus.LOCKED) {
            throw new DisabledException("Account is locked");
        }
    }

    private void handleFailedLoginAttempt(User user) {
        user.setFailedLoginAttempts(user.getFailedLoginAttempts() + 1);
        if (user.getFailedLoginAttempts() >= MAX_ATTEMPTS) {
            user.setAccountStatus(AccountStatus.LOCKED);
            user.setLockUntil(LocalDateTime.now().plusHours(24));
        }
        userRepository.save(user);
    }

    private void handleSuccessfulLogin(User user, DeviceInfo device) {
        user.setFailedLoginAttempts(0);
        user.setLastLoginAt(LocalDateTime.now());
        user.setLockUntil(null);
        device.setLastUsed(LocalDateTime.now());
        user.getTrustedDevices().add(device);
        userRepository.save(user);
    }

    private AuthResponse buildAuthResponse(User user) {
        return AuthResponse.builder()
                .accessToken(jwtService.generateToken(user))
                .refreshToken("refresh-temp") // Temporary until session implementation
                .email(user.getEmail())
                .role(user.getRole())
                .expiresAt(Instant.now().plusMillis(jwtService.getAccessExpirationMs()))
                .build();
    }
}