package com.proyecto.ventas.proyectoventasbazar.model;



import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@Entity
public class Producto {

       @Id
       @GeneratedValue(strategy = GenerationType.AUTO)
       private Long codigo_producto;
       private String nombre;
       private String marca;
       private double costo;
       private double stock;

       @OneToMany(mappedBy = "producto")
       private List<DetalleVenta> detalles;



       public Producto() {
       }

    public Producto(Long codigo_producto, String nombre, String marca, double costo, List<DetalleVenta> detalles) {
        this.codigo_producto = codigo_producto;
        this.nombre = nombre;
        this.marca = marca;
        this.costo = costo;
        this.detalles = detalles;
    }
}
