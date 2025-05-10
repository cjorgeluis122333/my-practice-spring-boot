package com.microservice.eureca.my_practice_springboot.controller.security.logout;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TokenBlacklistService {
    private final Map<String, Instant> blacklist = new ConcurrentHashMap<>();

    // Agrega el token al blacklist con su fecha de expiración
    public void blacklistToken(String token, Instant expiry) {
        blacklist.put(token, expiry);
    }

    // Comprueba si el token está en blacklist y elimina expirados
    public boolean isBlacklisted(String token) {
        Instant expiry = blacklist.get(token);
        if (expiry == null) {
            return false;
        }
        if (Instant.now().isAfter(expiry)) {
            blacklist.remove(token);
            return false;
        }
        return true;
    }
}

