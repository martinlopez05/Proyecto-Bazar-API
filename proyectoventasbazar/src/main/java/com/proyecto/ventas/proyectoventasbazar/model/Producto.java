package com.proyecto.ventas.proyectoventasbazar.model;



import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@Entity

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "codigoProducto")
public class Producto {

       @Id
       @GeneratedValue(strategy = GenerationType.SEQUENCE)
       private Long codigoProducto;
       private String nombre;
       private String marca;
       private double costo;
       private double stock;

       @OneToMany(mappedBy = "producto")
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
