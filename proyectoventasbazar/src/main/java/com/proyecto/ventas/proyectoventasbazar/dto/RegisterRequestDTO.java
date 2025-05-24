package com.proyecto.ventas.proyectoventasbazar.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterRequestDTO {

    @NotBlank
    @NotNull(message = "Username is required")
    private String username;

    @NotBlank
    @NotNull(message = "Password is required")
    private String password;

    @NotBlank
    @NotNull(message = "Role is required")
    private String role;

}
