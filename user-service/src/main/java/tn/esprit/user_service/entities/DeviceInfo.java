package tn.esprit.user_service.entities;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder // Add Lombok builder
public class DeviceInfo {
    private String fingerprintHash;
    private String browserHash;
    private String ipPrefix;
    private LocalDateTime firstSeen;
    private LocalDateTime lastUsed;
}