package tn.esprit.user_service.entities;

import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import tn.esprit.user_service.entities.enums.VerificationType;

import java.time.LocalDateTime;

@Document(collection = "verification_codes")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
public class VerificationCode extends BaseEntity {
    @Indexed(name = "expiration_ttl", expireAfter = "30m") // 30 minutes
    private LocalDateTime expiration;

    private String codeHash; // BCrypt hash of code
    private VerificationType type; // NEW enum
    private boolean used;

    // Changed to manual reference
    private String userId;
}