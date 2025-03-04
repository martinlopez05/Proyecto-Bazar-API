/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyecto.ventas.proyectoventasbazar.dto;

import java.util.List;
import lombok.Getter;

/**
 *
 * @author Usuario
 */
@Getter
public class ErrorDTOValidaciones extends ErrorDTO {
    
    private List<String> detalles;
    
    public ErrorDTOValidaciones(String mensaje, String codigo, List<String> detalles) {
        super(mensaje, codigo);
        this.detalles = detalles;
    }
    
}
