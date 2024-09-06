package com.proyecto.ventas.proyectoventasbazar.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetalleDTO {

    private Long codigo_producto;
    private int cantidad;

    public DetalleDTO(Long codigo_producto, int cantidad) {
        this.codigo_producto = codigo_producto;
        this.cantidad = cantidad;
    }

    public DetalleDTO() {
    }


}
