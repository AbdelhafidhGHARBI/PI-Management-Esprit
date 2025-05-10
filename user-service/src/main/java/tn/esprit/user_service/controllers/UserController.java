package tn.esprit.user_service.controllers;
import tn.esprit.user_service.service.EmailService;
import tn.esprit.user_service.service.servicesInterfaces.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.user_service.dto.UserCreateDto;
import tn.esprit.user_service.dto.UserResponseDto;

import java.util.HashMap;
import java.util.Map;


@RequestMapping("userdetails")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:8080"})
public class UserController {
    private final UserService userService;
    private final EmailService emailService;


//        GatewayAuthentication auth = (GatewayAuthentication)
//                SecurityContextHolder.getContext().getAuthentication();




}
