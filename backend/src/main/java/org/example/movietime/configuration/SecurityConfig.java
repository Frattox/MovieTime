package org.example.movietime.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtConverter jwtConverter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity
                .authorizeHttpRequests(auth -> auth

                        // AUTHCONTROLLER
                        .requestMatchers(HttpMethod.GET, "/v1/auth/login").authenticated()
                        .requestMatchers(HttpMethod.POST, "/v1/auth/signup").permitAll()
                        .requestMatchers(HttpMethod.POST, "cliente/*").permitAll()

                        // FILM CONTROLLER
                        .requestMatchers(HttpMethod.GET, "/film").permitAll()
                        .requestMatchers(HttpMethod.GET, "/film/*").permitAll()


                        // hasAnyRole(piu ruoli)
                        .anyRequest().authenticated())

                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtConverter)))
                .build();
    }
}
