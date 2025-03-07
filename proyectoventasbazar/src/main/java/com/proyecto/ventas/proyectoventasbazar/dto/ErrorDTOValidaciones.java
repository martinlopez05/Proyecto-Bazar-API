/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyecto.ventas.proyectoventasbazar.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 *
 * @author Usuario
 */
@Getter
@NoArgsConstructor
public class ErrorDTOValidaciones extends ErrorDTO {
    
    private List<String> details;

    public ErrorDTOValidaciones(List<String> details, String message, String errorCode, String detail, String path) {
        super(message, errorCode, detail, path);
        this.details = details;
    }    
}
