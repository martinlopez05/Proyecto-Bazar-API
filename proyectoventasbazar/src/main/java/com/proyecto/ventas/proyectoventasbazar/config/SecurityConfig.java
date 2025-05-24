package com.proyecto.ventas.proyectoventasbazar.config;

import com.proyecto.ventas.proyectoventasbazar.config.filter.JwtTokenValidator;
import com.proyecto.ventas.proyectoventasbazar.service.UserDetailServiceImpl;
import com.proyecto.ventas.proyectoventasbazar.utils.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.awt.*;
import java.net.http.HttpRequest;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtUtil jwtUtil;

    public SecurityConfig(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(http ->
                        http
                                .requestMatchers(HttpMethod.POST, "/auth/**").permitAll()


                                .requestMatchers(HttpMethod.POST, "/clientes/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/clientes/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/clientes/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/productos/**").hasRole("ADMIN")


                                .requestMatchers(HttpMethod.GET, "/productos/**").hasAnyRole("USER", "ADMIN", "INVITED")


                                .requestMatchers(HttpMethod.POST, "/detalles/**").hasAnyRole("USER", "ADMIN")
                                .requestMatchers(HttpMethod.POST, "/ventas/**").hasAnyRole("USER", "ADMIN")


                                .requestMatchers(HttpMethod.GET, "/ventas/**").hasAnyRole("USER", "ADMIN")

                                .anyRequest().authenticated()

                )
                .addFilterBefore(new JwtTokenValidator(jwtUtil), BasicAuthenticationFilter.class);


        return httpSecurity.build();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailServiceImpl userDetailService) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

        return daoAuthenticationProvider;

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
