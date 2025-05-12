package tn.esprit.user_service.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import tn.esprit.securityshared.auth.GatewayAuthentication;
import tn.esprit.user_service.dto.AuthResponse;
import tn.esprit.user_service.dto.LoginRequest;
import tn.esprit.user_service.dto.UserCreateDto;
import tn.esprit.user_service.dto.UserResponseDto;
import tn.esprit.user_service.entities.DeviceInfo;
import tn.esprit.user_service.service.servicesInterfaces.AuthService;
import tn.esprit.user_service.service.servicesInterfaces.DeviceMetadataService;
import tn.esprit.user_service.service.servicesInterfaces.UserService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:8080"})
public class AuthController {

    private final AuthService authService;
    private final DeviceMetadataService deviceService;
    private final UserService userService;

    @PostMapping("create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserCreateDto userCreateDto) {
        UserResponseDto response = userService.createUser(userCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }



    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody LoginRequest request,
            HttpServletRequest httpRequest
    ) {
        // extract device information
        DeviceInfo device = deviceService.extractDeviceInfo(httpRequest);
        return ResponseEntity.ok(authService.authenticate(request, device));
    }
}