package com.proyecto.ventas.proyectoventasbazar.controller;

import com.proyecto.ventas.proyectoventasbazar.dto.AuthRequestDTO;
import com.proyecto.ventas.proyectoventasbazar.dto.AuthResponseDTO;
import com.proyecto.ventas.proyectoventasbazar.dto.RegisterRequestDTO;
import com.proyecto.ventas.proyectoventasbazar.dto.RegisterResponseDTO;
import com.proyecto.ventas.proyectoventasbazar.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {


    @Autowired
    AuthService authService;

    @PostMapping("/register")
    public RegisterResponseDTO register(@RequestBody @Valid RegisterRequestDTO registerRequestDTO){
        return authService.register(registerRequestDTO);
    }

    @PostMapping("/login")
    public AuthResponseDTO login(@RequestBody @Valid AuthRequestDTO authRequestDTO){
        return authService.login(authRequestDTO);
    }

}
