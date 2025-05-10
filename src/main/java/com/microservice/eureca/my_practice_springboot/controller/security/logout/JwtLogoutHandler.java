package com.microservice.eureca.my_practice_springboot.controller.security.logout;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import java.time.Instant;

public class JwtLogoutHandler implements LogoutHandler {
    private final TokenBlacklistService blacklistService;
    private final JwtUtils jwtUtils;

    public JwtLogoutHandler(TokenBlacklistService blacklistService, JwtUtils jwtUtils) {
        this.blacklistService = blacklistService;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response,
                       Authentication authentication) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            Instant expiry = jwtUtils.getExpirationDateFromToken(token).toInstant();
            blacklistService.blacklistToken(token, expiry);
        }
    }
}


