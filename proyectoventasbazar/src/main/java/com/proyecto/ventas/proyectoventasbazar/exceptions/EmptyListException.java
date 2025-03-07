/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyecto.ventas.proyectoventasbazar.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 * @author Usuario
 */
@Getter
@AllArgsConstructor
public class EmptyListException extends RuntimeException{
    
    private String message;
    private String errorCode;
}
