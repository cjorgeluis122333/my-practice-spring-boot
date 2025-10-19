package com.microservice.eureca.my_practice_springboot.common.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservice.eureca.my_practice_springboot.common.utili.LoggerMessage;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;

import static com.microservice.eureca.my_practice_springboot.controller.security.util.TokenJwtConfig.SECRET_KEY;


public class GenerateToken {
    /**
     * This methode generate the claims from the Roles and the token with the username and the claims
     *
     * @param userDetails: I take the values username and authorities of the UserDetails for generate a token
     * @return Token with sign and claims
     */
    public static String generateToken(UserDetails userDetails) {
        Claims claims = null;
        try {
            claims = Jwts.claims()
                    .add("authorities", new ObjectMapper().writeValueAsString(userDetails.getAuthorities()))
                    .add("username", userDetails.getUsername())
                    .build();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        String token = Jwts.builder()
                .subject(userDetails.getUsername())
                .claims(claims)
                .expiration(new Date(System.currentTimeMillis() + 86400000))//one day
                .issuedAt(new Date())
                .signWith(SECRET_KEY)  //sign token
                .compact();

        LoggerMessage.showMessage(GenerateToken.class,token);
        return token;
    }
}
