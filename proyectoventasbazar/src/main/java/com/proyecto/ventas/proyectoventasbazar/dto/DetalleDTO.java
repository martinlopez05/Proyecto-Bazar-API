package com.proyecto.ventas.proyectoventasbazar.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetalleDTO {

    private Long idDetalle;
    private Long codigoProducto;
    private int cantidad;
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
