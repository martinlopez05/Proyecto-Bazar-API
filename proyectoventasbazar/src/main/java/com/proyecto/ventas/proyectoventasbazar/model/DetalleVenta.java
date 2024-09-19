package com.proyecto.ventas.proyectoventasbazar.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idDetalle")
public class DetalleVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idDetalle;

    @ManyToOne(optional = false)
    @JoinColumn(name="id_producto")
    //@JsonBackReference("producto-detalle")
    private Producto producto;

    @ManyToOne
    @JoinColumn(name="id_venta")
    //@JsonBackReference("venta-detalle")
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



    public DetalleVenta(Long idDetalle, Producto producto, Venta venta, int cantidad) {
        this.idDetalle = idDetalle;
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
