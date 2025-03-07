package com.proyecto.ventas.proyectoventasbazar.model;



import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


/**
 * Representa un producto.
 * Un producto tiene información como nombre, marca, costo y stock, y puede estar asociado a múltiples detalles de venta.
 */


@Getter
@Setter
@Entity

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "codigoProducto")
public class Producto {

       @Id
       @GeneratedValue(strategy = GenerationType.SEQUENCE)
       private Long codigoProducto;
       
       @Size(min = 2,max = 60 )
       @NotBlank(message = "el campo nombre no puede estar en blanco")
       private String nombre;
       
       @NotBlank(message="el campo marca no puede estar en blanco")
       private String marca;
       
       @PositiveOrZero(message = "el valor del costo debe ser mayor o igual a cero")
       @NotNull(message = "el campo costo no puede estar en blanco")
       private Double costo;
       
       @PositiveOrZero(message = "el valor de stock debe ser mayor o igual a cero")
       @NotNull(message = "el campo stock no puede estar en blanco")
       private Double stock;

       @OneToMany(mappedBy = "producto" ,orphanRemoval = true)
       @JsonIgnore
       private List<DetalleVenta> detallesProduc;



       public Producto() {
       }

       public Producto(Long codigoProducto, String nombre, String marca, double costo, List<DetalleVenta> detalles) {
          this.codigoProducto = codigoProducto;
          this.nombre = nombre;
          this.marca = marca;
          this.costo = costo;
          this.detallesProduc = detalles;
       }
}
