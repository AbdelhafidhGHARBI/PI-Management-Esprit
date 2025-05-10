package tn.esprit.user_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tn.esprit.user_service.dto.UserCreateDto;
import tn.esprit.user_service.dto.UserResponseDto;
import tn.esprit.user_service.entities.User;
import tn.esprit.user_service.entities.enums.AccountStatus;
import tn.esprit.user_service.entities.enums.Role;
import tn.esprit.user_service.exceptions.DuplicateEntityException;
import tn.esprit.user_service.repositories.UserRepository;
import tn.esprit.user_service.service.servicesInterfaces.UserService;
import tn.esprit.user_service.utils.PasswordGenerator;

import java.util.HashMap;
import java.util.Map;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImp implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Override
    public UserResponseDto createUser(UserCreateDto userCreateDto) {
        // Validate unique constraints
        if (userRepository.existsByEmail(userCreateDto.getEmail())) {
            throw new DuplicateEntityException("Email already exists");
        }
        if (userRepository.existsByCin(userCreateDto.getCin())) {
            throw new DuplicateEntityException("CIN already exists");
        }
        // Generate temporary password
        String tempPassword = PasswordGenerator.generateRandomPassword(12);
        // Create and save user
        User user = new User();
        user.setFirstName(userCreateDto.getFirstName());
        user.setLastName(userCreateDto.getLastName());
        user.setCin(userCreateDto.getCin());
        user.setEmail(userCreateDto.getEmail());
        user.setPasswordHash(passwordEncoder.encode(tempPassword));
        user.setRole(userCreateDto.getRole());
        user.setAccountStatus(AccountStatus.ACTIVE);

        User savedUser = userRepository.save(user);
        Map<String, Object> vars = new HashMap<>();
        vars.put("name", "John Doe");
        vars.put("password", tempPassword);
        emailService.sendHtmlEmail(
                "b.a.hamza1991@gmail.com",              // change this to your test email
                "Welcome to AuditXperts",
                "account-created", vars                    // without .html
        );

        // Update user with profile reference
//        savedUser.setProfile(savedProfile);
        userRepository.save(savedUser);



        return mapToUserResponseDto(savedUser);
    }

    private UserResponseDto mapToUserResponseDto(User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setCin(user.getCin());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        dto.setAccountStatus(user.getAccountStatus());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setPhoneNumber(user.getPhoneNumber());

        return dto;
    }
    @EventListener(ApplicationReadyEvent.class)
    public void createSuperUser() {
        String superUserEmail = "superadmin@example.com";

        if (!userRepository.existsByEmail(superUserEmail)) {
            try {
                // Generate strong password
                String password = "Esprit20252025.";

                User superUser = new User();
                superUser.setFirstName("Super");
                superUser.setLastName("Admin");
                superUser.setEmail(superUserEmail);
                superUser.setPasswordHash(passwordEncoder.encode(password));
                superUser.setRole(Role.ADMIN);
                superUser.setAccountStatus(AccountStatus.ACTIVE);
                superUser.setCin("00000000");  // Use a special CIN for superuser

                userRepository.save(superUser);

                log.info("Super user created successfully");
                log.info("Temporary super user password: {}", password);
            } catch (Exception e) {
                log.error("Error creating super user: {}", e.getMessage());
            }
        } else {
            log.info("Super user already exists, skipping creation");
        }
    }
}
