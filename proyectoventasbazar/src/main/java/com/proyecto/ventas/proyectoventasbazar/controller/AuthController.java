package com.proyecto.ventas.proyectoventasbazar.controller;

import com.proyecto.ventas.proyectoventasbazar.dto.AuthRequestDTO;
import com.proyecto.ventas.proyectoventasbazar.dto.AuthResponseDTO;
import com.proyecto.ventas.proyectoventasbazar.dto.RegisterRequestDTO;
import com.proyecto.ventas.proyectoventasbazar.dto.RegisterResponseDTO;
import com.proyecto.ventas.proyectoventasbazar.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador REST para gestionar operaciones relacionadas con el Registro y Autenticación del usuario.
 * Proporciona endpoints para realizar operaciones de Registro y Logueo.
 */

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticación Controller", description = "Operaciones relacionadas con registro y login de usuarios")
public class AuthController {


    @Autowired
    AuthService authService;


    @Operation(summary = "Registrar nuevo usuario", description = "Permite registrar un nuevo usuario en el sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Registro exitoso",
                    content = @Content(schema = @Schema(implementation = RegisterResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content)
    })
    @PostMapping("/register")
    public RegisterResponseDTO register(@RequestBody @Valid RegisterRequestDTO registerRequestDTO){
        return authService.register(registerRequestDTO);
    }


    @Operation(summary = "Login de usuario", description = "Permite a un usuario autenticarse y obtener un token de acceso")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login exitoso",
                    content = @Content(schema = @Schema(implementation = AuthResponseDTO.class))),
            @ApiResponse(responseCode = "401", description = "Credenciales inválidas", content = @Content)
    })
    @PostMapping("/login")
    public AuthResponseDTO login(@RequestBody @Valid AuthRequestDTO authRequestDTO){
        return authService.login(authRequestDTO);
    }

}
