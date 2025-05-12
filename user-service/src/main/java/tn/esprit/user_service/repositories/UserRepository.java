package tn.esprit.user_service.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.user_service.dto.UserResponseDto;
import tn.esprit.user_service.entities.User;

import java.util.Optional;



@Repository
public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);
    Optional<User> findByCin(String cin);
    boolean existsByEmail(String email);
    boolean existsByCin(String cin);
    boolean existsByEmailAndIdNot(String email, String id);
    boolean existsByCinAndIdNot(String cin, String id);
}