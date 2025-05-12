package tn.esprit.user_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.securityshared.auth.GatewayAuthentication;
import tn.esprit.user_service.dto.UserCreateDto;
import tn.esprit.user_service.dto.UserResponseDto;
import tn.esprit.user_service.dto.UserUpdateDto;
import tn.esprit.user_service.entities.User;
import tn.esprit.user_service.entities.enums.AccountStatus;
import tn.esprit.user_service.entities.enums.Role;
import tn.esprit.user_service.entities.enums.Sex;
import tn.esprit.user_service.exceptions.DuplicateEntityException;
import tn.esprit.user_service.mapper.UserMapper;
import tn.esprit.user_service.repositories.UserRepository;
import tn.esprit.user_service.service.servicesInterfaces.UserService;
import tn.esprit.user_service.utils.PasswordGenerator;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.logging.Level;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImp implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final GridFsTemplate gridFsTemplate;

    @Value("${app.profile-images-dir}")
    private String profileImagesDir;

    @Value("${spring.servlet.multipart.max-file-size}")
    private String maxFileSize;

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
        user.setPhoneNumber(userCreateDto.getPhoneNumber());
        user.setAccountStatus(AccountStatus.ACTIVE);
        user.setSex(userCreateDto.getSex());
        // temporary
        if(user.getRole().equals(Role.STUDENT)){
            user.setClassId("2");
        }
        User savedUser = userRepository.save(user);
        Map<String, Object> variables = new HashMap<>();
        variables.put("nom", user.getLastName());
        variables.put("prenom", user.getFirstName());
        variables.put("email", user.getEmail());
        variables.put("password", tempPassword);
        emailService.sendHtmlEmail(
                user.getEmail(),
                "Esprit-PI :  information sur le compte crée.",
                "account-created", variables
        );
        userRepository.save(savedUser);



        return UserMapper.mapToUserResponseDto(savedUser);

    }

    @Override
    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::mapToUserResponseDto)
                .collect(Collectors.toList());
    }
<<<<<<< HEAD

    @Override
    public Optional<User> findById(String id) {
        return  userRepository.findById(id) ;
    }

    @Override
    public void deleteUser(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));

        userRepository.delete(user);

    }

    @Override
    public User archiveUser(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));

        user.setAccountStatus(AccountStatus.ARCHIVED);
        return userRepository.save(user);
    }

    @Override
    public UserResponseDto updateUserByAdmin(String id, UserUpdateDto userUpdateDto) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé avec l'id: " + id));
        if (!existingUser.getEmail().equals(userUpdateDto.getEmail()) &&
                userRepository.existsByEmailAndIdNot(userUpdateDto.getEmail(), id)) {
            throw new DuplicateEntityException("Email déjà utilisé");
        }
        // CIN uniqueness check (skip current user)
        if (!existingUser.getCin().equals(userUpdateDto.getCin()) &&
                userRepository.existsByCinAndIdNot(userUpdateDto.getCin(), id)) {
            throw new DuplicateEntityException("CIN déjà utilisé");
        }
        // Update fields
        existingUser.setFirstName(userUpdateDto.getFirstName());
        existingUser.setLastName(userUpdateDto.getLastName());
        existingUser.setCin(userUpdateDto.getCin());
        existingUser.setEmail(userUpdateDto.getEmail());
        existingUser.setPhoneNumber(userUpdateDto.getPhoneNumber());
        existingUser.setRole(userUpdateDto.getRole());
        existingUser.setSex(userUpdateDto.getSex());
        existingUser.setAccountStatus(userUpdateDto.getAccountStatus());

        User updatedUser = userRepository.save(existingUser);
        return UserMapper.mapToUserResponseDto(updatedUser);
    }

    @Override
    public UserResponseDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé avec l'Email : " +email));
        return  UserMapper.mapToUserResponseDto(user);
    }

    @Override
    public UserResponseDto updateUserByHimself(String id, UserUpdateDto userUpdateDto) {
        // Retrieve the authenticated user's information
        GatewayAuthentication auth = (GatewayAuthentication) SecurityContextHolder.getContext().getAuthentication();
        String authenticatedUserId = auth.getUserId(); // Ensure this method exists in GatewayAuthentication

        // Verify the user is updating their own account
        if (!authenticatedUserId.equals(id)) {
            throw new AccessDeniedException("You are not authorized to update this user");
        }

        // Fetch the existing user
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));

        // Check CIN uniqueness if changed
        if (!existingUser.getCin().equals(userUpdateDto.getCin()) &&
                userRepository.existsByCinAndIdNot(userUpdateDto.getCin(), id)) {
            throw new DuplicateEntityException("CIN already exists");
        }

        // Update allowed fields: firstName, lastName, cin, phoneNumber
        existingUser.setFirstName(userUpdateDto.getFirstName());
        existingUser.setLastName(userUpdateDto.getLastName());
        existingUser.setCin(userUpdateDto.getCin());
        existingUser.setPhoneNumber(userUpdateDto.getPhoneNumber());

        // Save the updated user
        User updatedUser = userRepository.save(existingUser);
        return UserMapper.mapToUserResponseDto(updatedUser);
    }

    @Override
    public UserResponseDto updateProfileImage(String userId, MultipartFile file) {
        // Authorization check (existing code)
        GatewayAuthentication auth = (GatewayAuthentication) SecurityContextHolder.getContext().getAuthentication();
        if (!auth.getUserId().equals(userId)) {
            throw new AccessDeniedException("Unauthorized image upload");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Validate file
        if (file.isEmpty()) throw new IllegalArgumentException("File is empty");
        if (!Objects.requireNonNull(file.getContentType()).startsWith("image/")) {
            throw new IllegalArgumentException("Only images allowed");
        }

        // Validate file size
        DataSize maxSize = DataSize.parse(maxFileSize);
        if (file.getSize() > maxSize.toBytes()) {
            throw new IllegalArgumentException("File size exceeds " + maxFileSize);
        }

        // Generate unique filename
        String extension = getFileExtension(file.getContentType(), file.getOriginalFilename());
        String filename = UUID.randomUUID() + extension;

        // Prepare upload directory
        Path uploadPath = Paths.get(profileImagesDir).toAbsolutePath().normalize();
        try {
            Files.createDirectories(uploadPath);
        } catch (IOException ex) {
            throw new RuntimeException("Could not create upload directory", ex);
        }

        // Save the file
        Path targetPath = uploadPath.resolve(filename);
        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, targetPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            throw new RuntimeException("Failed to save image", ex);
        }

        // Delete old image
        if (user.getProfilePictureUrl() != null) {
            deleteOldImage(user.getProfilePictureUrl(), uploadPath);
        }

        // Update user entity
        user.setProfilePictureUrl("/api/users/profile_images/" + filename);
        User updatedUser = userRepository.save(user);

        return UserMapper.mapToUserResponseDto(updatedUser);
    }

    private String getFileExtension(String contentType, String originalFilename) {
        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
        } else {
            String type = contentType.split("/")[1];
            extension = switch (type) {
                case "jpeg" -> ".jpg";
                case "png" -> ".png";
                case "gif" -> ".gif";
                default -> "." + type;
            };
        }
        return extension;
    }

    private void deleteOldImage(String oldImageUrl, Path uploadPath) {
        String oldFilename = oldImageUrl.replace("/profile_images/", "");
        Path oldImagePath = uploadPath.resolve(oldFilename);
        try {
            Files.deleteIfExists(oldImagePath);
        } catch (IOException ex) {
            log.error("Failed to delete old image ", ex);
        }
    }



    @EventListener(ApplicationReadyEvent.class)
    public void createInitialUsers() {
        String defaultPassword = "Esprit20252025.";

        // 1. Create non-student users (admin, faculty, guest)
        List<User> usersToCreate = new ArrayList<>(List.of(
                createUser("Super", "Admin", "superadmin@example.com", "00000000", "+21600001111", Role.ADMIN, null),
                createUser("Bob", "Johnson", "bob@example.com", "22222222", "+21622223333", Role.FACULTY, null),
                createUser("Carol", "Brown", "carol@example.com", "33333333", "+21633334444", Role.GUEST, null)
        ));

        // 2. Create 30 students with class IDs 1,2,3
        List<User> students = new ArrayList<>();
        for (int i = 1; i <= 30; i++) {
            String classId = String.valueOf((i - 1) % 3 + 1); // Distribute 1,2,3
            students.add(createUser(
                    "Student",
                    "S" + String.format("%02d", i),  // Student S01, S02,... S30
                    "student" + i + "@example.com",
                    String.format("%08d", 10000000 + i), // Unique 8-digit CIN
                    "+2165555" + String.format("%04d", i), // Unique phone
                    Role.STUDENT,
                    classId
            ));
        }
        usersToCreate.addAll(students);

        // 3. Save all users
        for (User user : usersToCreate) {
            if (!userRepository.existsByEmail(user.getEmail())) {
                try {
                    user.setPasswordHash(passwordEncoder.encode(defaultPassword));
                    user.setAccountStatus(AccountStatus.ACTIVE);

                    // Additional student-specific setup
                    if (user.getRole() == Role.STUDENT) {
                        user.setClassId(user.getClassId()); // Already set in createUser
                    }

                    userRepository.save(user);
                    log.info("Created {} {} (Role: {}, Class: {})",
                            user.getFirstName(),
                            user.getLastName(),
                            user.getRole(),
                            user.getClassId());
                } catch (Exception e) {
                    log.error("Error creating user {}: {}", user.getEmail(), e.getMessage());
                }
            } else {
                // [Keep existing update logic for superadmin]
                if (user.getRole() == Role.ADMIN && user.getEmail().equals("superadmin@example.com")) {
                    try {
                        User existing = userRepository.findByEmail(user.getEmail())
                                .orElseThrow();
                        existing.setPasswordHash(passwordEncoder.encode(defaultPassword));
                        existing.setRole(Role.ADMIN);
                        existing.setAccountStatus(AccountStatus.ACTIVE);
                        userRepository.save(existing);
                        log.info("Reset superadmin credentials");
                    } catch (Exception e) {
                        log.error("Error updating superadmin: {}", e.getMessage());
                    }
                }
            }
        }
    }

    // Updated helper method with classId
    private User createUser(String firstName, String lastName, String email,
                            String cin, String phone, Role role, String classId) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setCin(cin);
        user.setPhoneNumber(phone);
        user.setRole(role);

        // Only set class ID for students
        if (role == Role.STUDENT) {
            user.setClassId(classId);
        }

        return user;
    }
=======
    @EventListener(ApplicationReadyEvent.class)
    public void createDevelopmentUsers() {
        Map<String, Role> usersToCreate = Map.of(
                "admin@example.com", Role.ADMIN,
                "faculty@example.com", Role.FACULTY,
                "student@example.com", Role.STUDENT
        );

        usersToCreate.forEach((email, role) -> {
            if (!userRepository.existsByEmail(email)) {
                try {
                    String password = generatePasswordForRole(role);

                    User user = new User();
                    user.setFirstName(role.name().charAt(0) + role.name().substring(1).toLowerCase()); // Capitalized role
                    user.setLastName("User");
                    user.setEmail(email);
                    user.setPasswordHash(passwordEncoder.encode(password));
                    user.setRole(role);
                    user.setAccountStatus(AccountStatus.ACTIVE);
                    user.setCin(generateCinForRole(role)); // Different CINs for each user

                    userRepository.save(user);

                    log.info("User created - Role: {}, Email: {}, Temporary Password: {}", role, email, password);
                } catch (Exception e) {
                    log.error("Error creating user with email {}: {}", email, e.getMessage());
                }
            } else {
                log.info("User with email {} already exists, skipping creation", email);
            }
        });
    }

    private String generatePasswordForRole(Role role) {
        // Simple password strategy for dev users
        return switch (role) {
            case ADMIN -> "Admin2025.";
            case FACULTY -> "Faculty2025.";
            case STUDENT -> "Student2025.";
            default -> "Guest2025.";
        };
    }

    private String generateCinForRole(Role role) {
        // Unique CINs based on role
        return switch (role) {
            case ADMIN -> "00000000";
            case FACULTY -> "11111111";
            case STUDENT -> "22222222";
            default -> "99999999";
        };
    }

>>>>>>> feature-Entity
}
