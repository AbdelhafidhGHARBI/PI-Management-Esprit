package tn.esprit.user_service.service.servicesInterfaces;

import tn.esprit.user_service.dto.UserCreateDto;
import tn.esprit.user_service.dto.UserResponseDto;

public interface UserService {
    UserResponseDto createUser(UserCreateDto userCreateDto);
}
