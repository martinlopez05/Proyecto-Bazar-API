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

/**
 * Clase utilitaria para manejar situaciones que lancen excepciones .
 * Proporciona métodos para validar valores de ID y verificar si las listas están vacías.
 */
public class ExceptionUtils {
    
    /**
     * Valida si un ID es un número positivo.
     * 
     * @param id El ID a validar.
     * @throws InvalidArgumentException Si el ID es menor o igual a cero.
     */
    public static void validateId(Long id) throws InvalidArgumentException {
        if (id <= 0) {
            throw new InvalidArgumentException("El id debe ser un numero positivo", "P-400");
        }
    }
    
    /**
     * Valida que una lista no esté vacía ni sea nula.
     * 
     * @param objects La lista de objetos a validar.
     * @param exceptionMessage El mensaje de excepción que se lanzará si la lista está vacía o es nula.
     * @throws EmptyListException Si la lista está vacía o es nula.
     */
    public static void validateListNotEmpty(List<?> objects, String exceptionMessage) throws EmptyListException{
        if(objects == null || objects.isEmpty()){
            throw new EmptyListException(exceptionMessage, "P-404");
        }
    }
    
}
