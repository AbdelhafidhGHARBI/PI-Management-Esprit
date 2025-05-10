//package tn.esprit.user_service.config;
//
//import lombok.Getter;
//import org.springframework.security.authentication.AbstractAuthenticationToken;
//import org.springframework.security.core.authority.AuthorityUtils;
//
//public class GatewayAuthentication extends AbstractAuthenticationToken {
//    private final String userId;
//    @Getter
//    private final String email;
//    @Getter
//    private final String role;
//
//    public GatewayAuthentication(String userId, String email, String role) {
//        super(AuthorityUtils.createAuthorityList("ROLE_" + role));
//        this.userId = userId;
//        this.email = email;
//        this.role = role;
//        setAuthenticated(true);
//    }
//
//    // Implement required methods
//    @Override
//    public Object getCredentials() { return null; }
//
//    @Override
//    public Object getPrincipal() { return userId; }
//
//}