package com.proyecto.ventas.proyectoventasbazar.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class VentaDTO {
       private Long codigoVenta;
       private Long idCliente;
       private List<DetalleDTO> detalles;

       private LocalDate fecha;

       private Double total;

       public VentaDTO() {
       }

       public VentaDTO(Long idCliente, List<DetalleDTO> detalles) {
              this.idCliente = idCliente;
              this.detalles = detalles;
       }

       public VentaDTO(Long idCliente, Long codigoVenta, List<DetalleDTO> detalles,Double total) {
              this.idCliente = idCliente;
              this.codigoVenta = codigoVenta;
              this.detalles = detalles;
              this.fecha = fecha;
              this.total= total;
       }


}
