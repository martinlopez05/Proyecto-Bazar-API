/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyecto.ventas.proyectoventasbazar.exceptions;

import com.proyecto.ventas.proyectoventasbazar.dto.ErrorDTO;
import com.proyecto.ventas.proyectoventasbazar.dto.ErrorDTOValidaciones;
import com.proyecto.ventas.proyectoventasbazar.exceptions.InvalidArgumentException;
import com.proyecto.ventas.proyectoventasbazar.exceptions.ResourceNotFoundException;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 *
 * @author Usuario
 */

@RestControllerAdvice
public class GlobalHandlerException{
    
    @ExceptionHandler(value = InvalidArgumentException.class)
    public ResponseEntity<ErrorDTO> handlerInvalidArgumentException(InvalidArgumentException ex){
        ErrorDTO error =  new ErrorDTO(ex.getMensaje(),ex.getCodigo());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity<ErrorDTO> handlerResourceNotFoundException(ResourceNotFoundException ex){
        ErrorDTO error = new ErrorDTO(ex.getMensaje(), ex.getCodigo());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND );
    }
    
    @ExceptionHandler ( value = MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTOValidaciones> handlerMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        List<String> errores = ex.getBindingResult()
                                .getFieldErrors()
                                .stream()
                                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                                .collect(Collectors.toList());

        ErrorDTOValidaciones error = new ErrorDTOValidaciones("Error de validación", "P-400", errores);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler ( value = Exception.class)
    public ResponseEntity<ErrorDTO> handlerGenericException(Exception ex){
        ErrorDTO error = new ErrorDTO("ocurrio un error inesperado", "P-500");
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
     
}
