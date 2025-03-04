/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyecto.ventas.proyectoventasbazar.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 *
 * @author Usuario
 */
@Getter
public class ResourceNotFoundException extends RuntimeException {
    
    private String mensaje;
    private String codigo;

    public ResourceNotFoundException(String mensaje, String codigo) {
        this.mensaje = mensaje;
        this.codigo = codigo;
    }

    
}
