package tn.esprit.securityshared.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import tn.esprit.securityshared.auth.GatewayAuthentication;

import java.io.IOException;

public class GatewayAuthFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(GatewayAuthFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();
        logger.info("🔐 GatewayAuthFilter intercepting path: {}", path);

        if (path.startsWith("/api/users/auth/login") || path.startsWith("/auth/login")) {
            logger.info("🟢 Public path, skipping auth filter.");
            filterChain.doFilter(request, response);
            return;
        }

        String userId = request.getHeader("X-User-Id");
        String email = request.getHeader("X-User-Email");
        String role = request.getHeader("X-User-Role");

        logger.info("Received headers: UserID={}, Email={}, Role={}", userId, email, role);

        if (userId == null || role == null) {
            logger.error("❌ Missing user headers. Aborting.");
            SecurityContextHolder.clearContext();
            throw new AuthenticationCredentialsNotFoundException("Missing security headers");
        }

        Authentication auth = new GatewayAuthentication(userId, email, role);
        SecurityContextHolder.getContext().setAuthentication(auth);
        logger.info("✅ Auth set for user: {}", userId);

        filterChain.doFilter(request, response);
    }
}
