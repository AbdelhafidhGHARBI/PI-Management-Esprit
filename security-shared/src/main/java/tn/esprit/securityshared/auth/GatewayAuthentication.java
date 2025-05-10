package tn.esprit.securityshared.auth;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;

public class GatewayAuthentication extends AbstractAuthenticationToken {
    private final String userId;
    private final String email;
    private final String role;

    public GatewayAuthentication(String userId, String email, String role) {
        super(AuthorityUtils.createAuthorityList("ROLE_" + role));
        this.userId = userId;
        this.email = email;
        this.role = role;
        setAuthenticated(true);
    }

    public String getUserId() { return userId; }
    public String getEmail() { return email; }
    public String getRole() { return role; }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return userId;
    }
}
