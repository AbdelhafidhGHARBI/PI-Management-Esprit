package tn.esprit.user_service.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import tn.esprit.user_service.entities.User;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);
    Optional<User> findByCin(String cin);
    boolean existsByEmail(String email);
    boolean existsByCin(String cin);
}