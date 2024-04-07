package com.example.springoauth2clientsession.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        //csrf
        httpSecurity
                .csrf((csrf) -> csrf.disable());
        //form Login
        httpSecurity
                .formLogin((login) -> login.disable());
        //http Basic
        httpSecurity
                .httpBasic((basic) -> basic.disable());
        //Oauth2
        httpSecurity
                .oauth2Login(Customizer.withDefaults());

        httpSecurity
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/", "/oauth2/**", "/login/**").permitAll()
                        .anyRequest().authenticated());

        return httpSecurity.build();
    }
}
