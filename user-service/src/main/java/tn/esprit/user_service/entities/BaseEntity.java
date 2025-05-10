package tn.esprit.user_service.entities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.*;
import java.time.LocalDateTime;

@Getter
@ToString
public abstract class BaseEntity {

    @Id
    @Setter(AccessLevel.PROTECTED)
    private String id;

    @CreatedDate
    @Setter(AccessLevel.PROTECTED)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Setter(AccessLevel.PROTECTED)
    private LocalDateTime updatedAt;
}