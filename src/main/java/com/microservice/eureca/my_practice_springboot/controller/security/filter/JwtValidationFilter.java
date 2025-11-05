package com.microservice.eureca.my_practice_springboot.controller.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservice.eureca.my_practice_springboot.controller.security.logout.JwtUtils;
import com.microservice.eureca.my_practice_springboot.controller.security.logout.TokenBlacklistService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.microservice.eureca.my_practice_springboot.controller.security.util.TokenJwtConfig.*;

public class JwtValidationFilter extends BasicAuthenticationFilter {

    private final TokenBlacklistService blacklistService;
    private final JwtUtils jwtUtils;

    public JwtValidationFilter(AuthenticationManager authManager,
                               TokenBlacklistService blacklistService,
                               JwtUtils jwtUtils) {
        super(authManager);
        this.blacklistService = blacklistService;
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String header = request.getHeader(HEADER_AUTHORIZATION);
        if (header == null || !header.startsWith(BEARER)) {
            chain.doFilter(request, response);
            return;
        }
        String token = header.replace(BEARER, "");

        // Verificar blacklist
        if (blacklistService.isBlacklisted(token)) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(CONTENT_TYPE);
            response.getWriter().write("{\"error\":\"Token revocado\",\"message\":\"El JWT ha sido revocado\"}");
            return;
        }

        try {
            // Validación normal con JwtUtils
            Authentication auth = jwtUtils.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(auth);
            chain.doFilter(request, response);
        } catch (JwtException e) {
            Map<String, String> body = new HashMap<>();
            body.put("error", e.getMessage());
            body.put("message", "El token JWT es invalido!");

            response.getWriter().write(new ObjectMapper().writeValueAsString(body));
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(CONTENT_TYPE);
        }
    }
}
