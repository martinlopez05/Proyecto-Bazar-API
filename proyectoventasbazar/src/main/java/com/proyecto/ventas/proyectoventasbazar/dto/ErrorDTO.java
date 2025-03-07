/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyecto.ventas.proyectoventasbazar.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 *
 * @author Usuario
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDTO {
   
    private String message;
    private String errorCode;
    private LocalDateTime timestamp;
    private String detail;
    private String path;

    public ErrorDTO(String message, String errorCode, String detail , String path) {
        this.message = message;
        this.errorCode = errorCode;
        this.detail = detail;
        this.path = path;
        this.timestamp = LocalDateTime.now();
    }
              
}
