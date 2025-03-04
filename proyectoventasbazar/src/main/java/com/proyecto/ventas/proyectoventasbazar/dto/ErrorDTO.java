/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyecto.ventas.proyectoventasbazar.dto;

import lombok.Getter;

/**
 *
 * @author Usuario
 */
@Getter
public class ErrorDTO {
   
    private String mensaje;
    private String codigo;

    public ErrorDTO(String mensaje, String codigo) {
        this.mensaje = mensaje;
        this.codigo = codigo;
    }
        
}
