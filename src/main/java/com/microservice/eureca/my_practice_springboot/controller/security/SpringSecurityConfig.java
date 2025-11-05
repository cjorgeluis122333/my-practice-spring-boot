package com.microservice.eureca.my_practice_springboot.controller.security;

import com.microservice.eureca.my_practice_springboot.controller.security.filter.JwtAuthenticationFilter;
import com.microservice.eureca.my_practice_springboot.controller.security.filter.JwtValidationFilter;
import com.microservice.eureca.my_practice_springboot.controller.security.logout.JwtLogoutHandler;
import com.microservice.eureca.my_practice_springboot.controller.security.logout.JwtUtils;
import com.microservice.eureca.my_practice_springboot.controller.security.logout.TokenBlacklistService;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.List;

import static com.microservice.eureca.my_practice_springboot.controller.security.util.TokenJwtConfig.HEADER_AUTHORIZATION;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final TokenBlacklistService blacklistService;
    private final JwtUtils jwtUtils;

    public SpringSecurityConfig(AuthenticationConfiguration authenticationConfiguration,
                                TokenBlacklistService blacklistService,
                                JwtUtils jwtUtils) {
        this.authenticationConfiguration = authenticationConfiguration;
        this.blacklistService = blacklistService;
        this.jwtUtils = jwtUtils;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/api/register").permitAll()
                        //Delete soon
                        .requestMatchers(HttpMethod.GET, "/api/student/all").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/student/").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/student/").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/student/").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/student/").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilter(new JwtAuthenticationFilter(authenticationManager()))
                .addFilter(new JwtValidationFilter(authenticationManager(), blacklistService, jwtUtils))
                .logout(logout -> logout
                        .logoutUrl("/api/logout")
                        .addLogoutHandler(new JwtLogoutHandler(blacklistService, jwtUtils))
                        .logoutSuccessHandler((request, response, authentication) ->
                                response.setStatus(HttpStatus.OK.value())
                        )
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors(cors -> cors.configurationSource(corsConfigurationSource()));

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOriginPatterns(List.of("*"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "PUT"));
        config.setAllowedHeaders(Arrays.asList(HEADER_AUTHORIZATION, "Content-Type"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter() {
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(
                new CorsFilter(corsConfigurationSource())
        );
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }
}
