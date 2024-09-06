package com.proyecto.ventas.proyectoventasbazar.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class VentaDTO {
       private Long id_cliente;
       private List<DetalleDTO> detalles;
       private LocalDate fecha;

       public VentaDTO() {
       }

       public VentaDTO(Long id_cliente, List<DetalleDTO> detalles, LocalDate fecha) {
              this.id_cliente = id_cliente;
              this.detalles = detalles;
              this.fecha = fecha;
       }
}
