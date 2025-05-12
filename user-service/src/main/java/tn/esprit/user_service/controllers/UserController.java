package tn.esprit.user_service.controllers;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.user_service.dto.UserUpdateDto;
import tn.esprit.user_service.entities.User;
import tn.esprit.user_service.mapper.UserMapper;
import tn.esprit.user_service.service.servicesInterfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.user_service.dto.UserResponseDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RequestMapping("user")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:8080"})
public class UserController {
    private final UserService userService;


    // start endpoint for Admin user

    @GetMapping("all")
    @PreAuthorize("hasRole('ADMIN') or hasRole('FACULTY')")
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
       return ResponseEntity.ok(userService.getAllUsers());
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "User deleted successfully");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable String id) {
        User user = userService.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id)) ;
        return ResponseEntity.ok(UserMapper.mapToUserResponseDto(user)) ;

    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponseDto> updateUser(
            @PathVariable String id,
            @Valid @RequestBody UserUpdateDto userUpdateDto
    ) {
        UserResponseDto updatedUser = userService.updateUserByAdmin(id, userUpdateDto);
        return ResponseEntity.ok(updatedUser);
    }


    @PutMapping("/{id}/archive")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponseDto> archiveUser(@PathVariable String id) {
        User user = userService.archiveUser(id) ;
        UserResponseDto responseDto = UserMapper.mapToUserResponseDto(user) ;
        return ResponseEntity.ok(responseDto);
    }


    // end endpoint for Admin user




    //Start EndPoint for Commun user

    @GetMapping("/commun/by-email/{email}")
    public ResponseEntity<UserResponseDto> getUserByEmail(@PathVariable String email) {
        UserResponseDto user = userService.getUserByEmail(email);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/commun/{id}")
    public ResponseEntity<UserResponseDto> updateUserByHimself(
            @PathVariable String id  ,
            @Valid @RequestBody UserUpdateDto userUpdateDto
    ) {
        UserResponseDto user = userService.updateUserByHimself(id, userUpdateDto);
        return ResponseEntity.ok(user);
    }


    @PutMapping("/commun/{id}/profile-image")
    public ResponseEntity<UserResponseDto> uploadProfileImage(
            @PathVariable String id,
            @RequestParam("file") MultipartFile file) {

        UserResponseDto updatedUser = userService.updateProfileImage(id, file);
        return ResponseEntity.ok(updatedUser);
    }


}
