package com.proyecto.ventas.proyectoventasbazar.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetalleDTO {

    private Long codigoProducto;
    private int cantidad;

    public DetalleDTO(Long codigoProducto, int cantidad) {
        this.codigoProducto = codigoProducto;
        this.cantidad = cantidad;
    }

    public DetalleDTO() {
    }


}
