package tn.esprit.user_service.service.servicesInterfaces;

import tn.esprit.user_service.dto.AuthResponse;
import tn.esprit.user_service.dto.LoginRequest;
import tn.esprit.user_service.entities.DeviceInfo;

public interface AuthService {
    AuthResponse authenticate(LoginRequest request , DeviceInfo http ) ;
}
