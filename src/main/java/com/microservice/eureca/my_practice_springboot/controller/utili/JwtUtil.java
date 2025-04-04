package com.microservice.eureca.my_practice_springboot.controller.utili;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import static com.microservice.eureca.my_practice_springboot.controller.security.TokenJwtConfig.SECRET_KEY;

public class JwtUtil {

    // Definición de la clave secreta usando Jwts.SIG.HS256.key().build()
//    public static final SecretKey SECRET_KEY = Jwts.SIG.HS256.key().build();

    public static Claims extraerClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(SECRET_KEY)  // Utilizamos la clave directamente
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (Exception e) {
            throw new RuntimeException("Error al analizar el token JWT", e);
        }
    }

    public static String getUsernameFormToken(String token) {
        Claims claims = extraerClaims(token);

        // Obtener el username desde el claim "sub" (subject)
        String username = claims.getSubject();
        return username;
    }

}
