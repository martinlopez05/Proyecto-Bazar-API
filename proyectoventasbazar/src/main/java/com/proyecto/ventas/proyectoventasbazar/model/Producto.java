package com.proyecto.ventas.proyectoventasbazar.model;



import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
public class Producto {

       @Id
       @GeneratedValue(strategy = GenerationType.AUTO)
       private Long codigo_producto;
       private String nombre;
       private String marca;
       private Double costo;
       private Double stock;

       @ManyToOne
       @JoinColumn(name = "id_venta")
       private Venta venta;

       public Producto(Long codigo_producto, String nombre, String marca, Double costo, Double stock) {
           this.codigo_producto = codigo_producto;
           this.nombre = nombre;
           this.marca = marca;
           this.costo = costo;
           this.stock = stock;
       }

       public Producto() {
       }



}
