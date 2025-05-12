package tn.esprit.user_service.service;

import com.google.common.hash.Hashing;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tn.esprit.user_service.dto.AuthResponse;
import tn.esprit.user_service.dto.LoginRequest;
import tn.esprit.user_service.entities.DeviceInfo;
import tn.esprit.user_service.entities.Session;
import tn.esprit.user_service.entities.User;
import tn.esprit.user_service.entities.enums.AccountStatus;
import tn.esprit.user_service.repositories.SessionRepository;
import tn.esprit.user_service.repositories.UserRepository;
import tn.esprit.user_service.service.servicesInterfaces.AuthService;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthInterfaceImpl implements AuthService {

    private static final int MAX_ATTEMPTS = 5;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;

    @Override
    public AuthResponse authenticate(LoginRequest request, DeviceInfo device) {
        String message = "Le compte ou le mot de passe est érronée !!! " ;
        try {
            // check user exist or not
            User user = userRepository.findByEmail(request.email())
                    .orElseThrow(() -> new BadCredentialsException(message));
            // check account status
            checkAccountStatus(user);
            validatePassword(request, user);
            handleSuccessfulLogin(user, device);

            return createAuthResponse(user, device);
        } catch (UsernameNotFoundException e) {
            throw new BadCredentialsException(message);
        }
    }
    private AuthResponse createAuthResponse(User user, DeviceInfo device) {
        // Generate tokens
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        // Create session
        Session session = new Session();
        session.setRefreshTokenHash(hashRefreshToken(refreshToken));
        session.setDeviceFingerprintHash(device.getFingerprintHash());
        session.setIpPrefix(device.getIpPrefix());
        session.setExpiresAt(LocalDateTime.now().plusSeconds(jwtService.getRefreshExpirationMs() / 1000));
        session.setUserId(user.getId());
        sessionRepository.save(session);

        // Build response
        return new AuthResponse(
                accessToken,
                refreshToken,
                Instant.now().plusMillis(jwtService.getAccessExpirationMs()),
                Instant.now().plusMillis(jwtService.getRefreshExpirationMs()),
                user.getEmail(),
                user.getRole()
        );
    }
    private void checkAccountStatus(User user) {
        if (!user.isEnabled()) {
            throw new DisabledException("Account is not active");
        }
        if (user.getAccountStatus() == AccountStatus.LOCKED) {
            throw new DisabledException("Le compte est fermé, veuillez contacter l'administration!");
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
        log.info(device.toString());
        user.setFailedLoginAttempts(0);
        user.setLastLoginAt(LocalDateTime.now());
        user.setLockUntil(null);
        device.setLastUsed(LocalDateTime.now());
        user.getTrustedDevices().add(device);
        userRepository.save(user);
    }


    private String hashRefreshToken(String token) {
        return Hashing.sha256().hashString(token, StandardCharsets.UTF_8).toString();
    }


    private void validatePassword(LoginRequest request, User user) {
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            handleFailedLoginAttempt(user);
            throw new BadCredentialsException("Invalid credentials");
        }
    }
}