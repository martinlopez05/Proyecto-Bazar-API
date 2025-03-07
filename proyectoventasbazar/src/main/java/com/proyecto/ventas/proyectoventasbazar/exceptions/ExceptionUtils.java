/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyecto.ventas.proyectoventasbazar.exceptions;

import java.util.List;

/**
 *
 * @author Usuario
 */
public class ExceptionUtils {
    
    public static void validateId(Long id) throws InvalidArgumentException {
        if (id <= 0) {
            throw new InvalidArgumentException("El id debe ser un numero positivo", "P-400");
        }
    }
    
    public static void validateListNotEmpty(List<?> objects, String exceptionMessage){
        if(objects == null || objects.isEmpty()){
            throw new EmptyListException(exceptionMessage, "P-404");
        }
    }
    
}
