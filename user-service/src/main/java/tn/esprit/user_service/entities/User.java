package tn.esprit.user_service.entities;

import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import tn.esprit.user_service.entities.enums.AccountStatus;
import tn.esprit.user_service.entities.enums.Role;
import tn.esprit.user_service.entities.enums.ThemePreference;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Document(collection = "users")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
public class User extends BaseEntity  implements UserDetails {


    private String firstName;
    private String lastName;

    @Indexed(unique = true)
    private String cin;

    @Indexed(unique = true)
    private String email;

//    @Setter(AccessLevel.NONE) // Prevent direct modification
    private String passwordHash;
    private String bio;
    private String phoneNumber;
    private ThemePreference theme = ThemePreference.LIGHT;
    private String profilePictureUrl;
    private Role role = Role.STUDENT;
    private AccountStatus accountStatus = AccountStatus.PENDING;
    private int failedLoginAttempts = 0;
    private LocalDateTime lockUntil;
    private LocalDateTime lastLoginAt; // NEW


    // NEW: Device tracking
    private List<DeviceInfo> trustedDevices = new ArrayList<>();

    // NEW: Token versioning
    private Integer tokenVersion = 0;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return passwordHash; // Return actual stored hash
    }

    @Override
    public String getUsername() {
        return email; // Use email as username
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountStatus != AccountStatus.LOCKED;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return accountStatus == AccountStatus.ACTIVE;
    }
}