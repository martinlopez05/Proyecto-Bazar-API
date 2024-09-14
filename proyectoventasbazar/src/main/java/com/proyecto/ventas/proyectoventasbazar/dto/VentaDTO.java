package com.proyecto.ventas.proyectoventasbazar.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class VentaDTO {
       private Long idCliente;
       private List<DetalleDTO> detalles;
       private LocalDate fecha;

       public VentaDTO() {
       }

       public VentaDTO(Long idCliente, List<DetalleDTO> detalles, LocalDate fecha) {
              this.idCliente = idCliente;
              this.detalles = detalles;
              this.fecha = fecha;
       }
}
