//package tn.esprit.user_service.config;
//
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.NonNull;
//import lombok.extern.slf4j.Slf4j;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//
//
//@Component
//public class GatewayAuthFilter extends OncePerRequestFilter {
//
//    private static final Logger logger = LoggerFactory.getLogger(GatewayAuthFilter.class);
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request,
//                                    HttpServletResponse response,
//                                    FilterChain filterChain) throws ServletException, IOException {
//        String userId = request.getHeader("X-User-Id");
//        String email = request.getHeader("X-User-Email");
//        String role = request.getHeader("X-User-Role");
//        String path = request.getRequestURI();
//        logger.info("\n Received headers - Path : {}" , path);
//
//        if (path.startsWith("/api/users/auth/login")) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//        logger.info("\n Received headers - UserID: {}, Email: {}, Role: {}", userId, email, role);
//        if (userId == null || role == null) {
//            SecurityContextHolder.clearContext();
//            throw new AuthenticationCredentialsNotFoundException("Missing security headers");
//        }
//
//        if (userId != null && role != null) {
//            Authentication auth = new GatewayAuthentication(userId, email, role);
//            SecurityContextHolder.getContext().setAuthentication(auth);
//            logger.debug("Set authentication for user: {}", userId);
//        } else {
//            logger.warn("Missing required user headers");
//        }
//
//        filterChain.doFilter(request, response);
//    }
//}