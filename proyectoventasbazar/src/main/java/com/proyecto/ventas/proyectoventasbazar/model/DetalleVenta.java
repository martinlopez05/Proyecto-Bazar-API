package com.proyecto.ventas.proyectoventasbazar.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
public class DetalleVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_detalle;

    @ManyToOne
    @JoinColumn(name="id_producto")
    @JsonBackReference
    private Producto producto;

    @ManyToOne
    @JoinColumn(name="id_venta")
    @JsonBackReference
    private Venta venta;

    private int cantidad;
    private double precio;


    public DetalleVenta() {
    }

    public DetalleVenta(Producto producto){
        this.producto=producto;

    }



    public DetalleVenta(Long id_detalle, Producto producto, Venta venta, int cantidad,double precio) {
        this.id_detalle = id_detalle;
        this.producto = producto;
        this.venta = venta;
        this.cantidad = cantidad;
        this.precio = calcularPrecio();

    }


    public double calcularPrecio() {
        if (producto == null) {
            throw new IllegalStateException("Producto no está inicializado");
        }
        return producto.getCosto();
    }


}
