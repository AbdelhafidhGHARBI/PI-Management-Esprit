package tn.esprit.user_service.dto;


import lombok.Builder;
import lombok.Data;
import tn.esprit.user_service.entities.enums.AccountStatus;
import tn.esprit.user_service.entities.enums.Role;
import tn.esprit.user_service.entities.enums.Sex;

import java.time.LocalDateTime;

@Data
@Builder
public class UserResponseDto {
    private String id;
    private String firstName;
    private String lastName;
    private String cin;
    private String email;
    private String phoneNumber;
    private Role role;
    private AccountStatus accountStatus;
    private LocalDateTime createdAt;
    private String profilePictureUrl; 
    private Sex sex  ;
    private String classId ;
 }