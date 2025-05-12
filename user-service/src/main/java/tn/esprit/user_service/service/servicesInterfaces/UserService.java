package tn.esprit.user_service.service.servicesInterfaces;

import org.springframework.web.multipart.MultipartFile;
import tn.esprit.user_service.dto.UserCreateDto;
import tn.esprit.user_service.dto.UserResponseDto;
import tn.esprit.user_service.dto.UserUpdateDto;
import tn.esprit.user_service.entities.User;
import tn.esprit.user_service.entities.enums.Sex;

import java.util.List;
import java.util.Optional;

public interface UserService  {
    UserResponseDto createUser(UserCreateDto userCreateDto);
    List<UserResponseDto> getAllUsers();
    Optional<User> findById(String id);
    void deleteUser(String id);
    User archiveUser(String id);
    UserResponseDto updateUserByAdmin(String id, UserUpdateDto userUpdateDto);
    UserResponseDto getUserByEmail(String email);
    UserResponseDto updateUserByHimself(String id, UserUpdateDto userUpdateDto);

    UserResponseDto updateProfileImage(String userId, MultipartFile file);
}
