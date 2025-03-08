/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyecto.ventas.proyectoventasbazar.exceptions;

import com.proyecto.ventas.proyectoventasbazar.dto.ErrorDTO;
import com.proyecto.ventas.proyectoventasbazar.dto.ErrorDTOValidaciones;
import com.proyecto.ventas.proyectoventasbazar.exceptions.InvalidArgumentException;
import com.proyecto.ventas.proyectoventasbazar.exceptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
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
public class GlobalHandlerException {

    /**
     * Maneja excepciones de tipo {@link InvalidArgumentException}. Este método
     * se ejecuta cuando se lanza una {@link InvalidArgumentException} en la
     * aplicación.
     *
     * @param ex La excepción capturada de tipo
     * {@link InvalidArgumentException}.
     * @param request La solicitud HTTP que causó la excepción.
     * @return Un {@link ResponseEntity} con un objeto {@link ErrorDTO} que
     * contiene detalles del error y un código de estado HTTP 400 (BAD REQUEST).
     */
    @ExceptionHandler(value = InvalidArgumentException.class)
    public ResponseEntity<?> handlerInvalidArgumentException(InvalidArgumentException ex, HttpServletRequest request) {
        ErrorDTO error = new ErrorDTO(ex.getMenssage(), ex.getErrorCode(), "Solicitud invalida", request.getRequestURI());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * Maneja excepciones de tipo {@link ResourceNotFoundException}. Este método
     * se ejecuta cuando se lanza una {@link ResourceNotFoundException} en la
     * aplicación.
     *
     * @param ex La excepción capturada de tipo
     * {@link ResourceNotFoundException}.
     * @param request La solicitud HTTP que causó la excepción.
     * @return Un {@link ResponseEntity} con un objeto {@link ErrorDTO} que
     * contiene detalles del error y un código de estado HTTP 404 (NOT FOUND).
     */
    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity<?> handlerResourceNotFoundException(ResourceNotFoundException ex, HttpServletRequest request) {
        ErrorDTO error = new ErrorDTO(ex.getMenssage(), ex.getErrorCode(), "Recurso no encontrado", request.getRequestURI());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    /**
     * Maneja excepciones de tipo {@link MethodArgumentNotValidException}. Este
     * método se ejecuta cuando los datos enviados en una solicitud no cumplen
     * con las validaciones definidas.
     *
     * @param ex La excepción capturada de tipo
     * {@link MethodArgumentNotValidException}.
     * @param request La solicitud HTTP que causó la excepción.
     * @return Un {@link ResponseEntity} con un objeto
     * {@link ErrorDTOValidaciones} que contiene una lista de errores de
     * validación y un código de estado HTTP 400 (BAD REQUEST).
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<?> handlerMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        List<String> errores = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .collect(Collectors.toList());

        ErrorDTOValidaciones error = new ErrorDTOValidaciones(errores, "Los datos enviados en la solicitud son incorrectos", "P-400",
                "Error de validacion en la solicitud", request.getRequestURI());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * Maneja excepciones de tipo {@link EmptyListException}. Este método se
     * ejecuta cuando se lanza una {@link EmptyListException} en la aplicación.
     *
     * @param ex La excepción capturada de tipo {@link EmptyListException}.
     * @param request La solicitud HTTP que causó la excepción.
     * @return Un {@link ResponseEntity} con un objeto {@link ErrorDTO} que
     * contiene detalles del error y un código de estado HTTP 404 (NOT FOUND).
     */
    @ExceptionHandler(value = EmptyListException.class)
    public ResponseEntity<?> handlerEmptyListException(EmptyListException ex, HttpServletRequest request) {
        ErrorDTO error = new ErrorDTO(ex.getMessage(), ex.getErrorCode(), "Recurso no encontrado", request.getRequestURI());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    /**
     * Maneja excepciones genéricas no capturadas por otros manejadores. Este
     * método se ejecuta cuando se lanza una excepción no controlada en la
     * aplicación.
     *
     * @param ex La excepción capturada.
     * @param request La solicitud HTTP que causó la excepción.
     * @return Un {@link ResponseEntity} con un objeto {@link ErrorDTO} que
     * contiene detalles del error y un código de estado HTTP 500 (INTERNAL
     * SERVER ERROR).
     */
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<?> handlerGenericException(Exception ex, HttpServletRequest request) {
        ErrorDTO error = new ErrorDTO("ocurrio un error inesperado", "P-500", "Internal Error Server", request.getRequestURI());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Maneja excepciones de tipo {@link InsufficientStockException}. Este
     * método se ejecuta cuando se lanza una {@link InsufficientStockException}
     * en la aplicación.
     *
     * @param ex La excepción capturada de tipo
     * {@link InsufficientStockException}.
     * @param request La solicitud HTTP que causó la excepción.
     * @return Un {@link ResponseEntity} con un objeto {@link ErrorDTO} que
     * contiene detalles del error y un código de estado HTTP 400 (BAD REQUEST).
     */
    @ExceptionHandler(value = InsufficientStockException.class)
    public ResponseEntity<?> handlerInsufficientStockException(InsufficientStockException ex, HttpServletRequest request) {
        ErrorDTO error = new ErrorDTO(ex.getMessage(), ex.getErrorCode(), "Stock insuficiente del producto", request.getRequestURI());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
