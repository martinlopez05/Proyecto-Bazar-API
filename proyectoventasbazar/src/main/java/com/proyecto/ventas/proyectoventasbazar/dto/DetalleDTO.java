package com.proyecto.ventas.proyectoventasbazar.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetalleDTO {

    private Long idDetalle;
    
    @NotNull(message = " el codigo del producto no puede estar vacío")
    private Long codigoProducto;
    
    @Min(value = 1 , message = "La cantidad debe ser mayor a cero")
  
    private int cantidad;
    
    @Min(value = 1 , message = "La cantidad debe ser mayor a cero")
    private double precio;

    public DetalleDTO(Long codigoProducto, int cantidad) {
        this.codigoProducto = codigoProducto;
        this.cantidad = cantidad;
    }

    public DetalleDTO(Long idDetalle) {
        this.idDetalle = idDetalle;
    }

    public DetalleDTO(Long idDetalle, Long codigoProducto, int cantidad,double precio) {
        this.idDetalle = idDetalle;
        this.codigoProducto = codigoProducto;
        this.cantidad = cantidad;
        this.precio=precio;
    }

    public DetalleDTO() {
    }


}
