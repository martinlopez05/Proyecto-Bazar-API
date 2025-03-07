/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyecto.ventas.proyectoventasbazar.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 *
 * @author Usuario
 */
@Getter
@AllArgsConstructor
public class InvalidArgumentException extends RuntimeException {
    
    private String menssage;
    private String errorCode;
    
}
