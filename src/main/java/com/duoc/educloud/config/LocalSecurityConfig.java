package com.duoc.educloud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@Profile("local")
public class LocalSecurityConfig {

    @Bean
    public SecurityFilterChain localSecurityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/h2-console/**").permitAll()
                        .requestMatchers("/api/cursos/**").permitAll()
                        .requestMatchers("/api/inscripciones/**").permitAll()
                        .anyRequest().denyAll()
                )

                .headers(headers ->
                        headers.frameOptions(frameOptions -> frameOptions.sameOrigin())
                );

        return http.build();
    }
}