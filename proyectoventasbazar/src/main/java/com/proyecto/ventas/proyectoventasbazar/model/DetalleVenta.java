package com.proyecto.ventas.proyectoventasbazar.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @ManyToOne(optional = false)
    @JoinColumn(name="id_producto")
    private Producto producto;

    @ManyToOne
    @JoinColumn(name="id_venta")
    private Venta venta;

    private int cantidad;
    private double precio;


    public DetalleVenta() {
    }

    public DetalleVenta(Producto producto,int cantidad){
        this.producto=producto;
        this.cantidad = cantidad;
        inicializarPrecio();

    }



    public DetalleVenta(Long id_detalle, Producto producto, Venta venta, int cantidad) {
        this.id_detalle = id_detalle;
        this.producto = producto;
        this.venta = venta;
        this.cantidad = cantidad;
        inicializarPrecio();


    }

    private void inicializarPrecio() {
        if (producto == null) {
            throw new IllegalStateException("Producto no está inicializado");
        }
        this.precio = producto.getCosto() * cantidad;
    }



}
