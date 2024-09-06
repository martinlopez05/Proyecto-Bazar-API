package com.proyecto.ventas.proyectoventasbazar.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Entity
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long codigo_venta;
    private LocalDate fecha_venta;
    private Double total;


    @ManyToOne(optional = false)
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;


    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL ,orphanRemoval = true)
    private List<DetalleVenta> detalles = new ArrayList<>();

    public Venta() {
    }

    public Venta(Long codigo_venta, LocalDate fecha_venta,Cliente cliente) {
        this.codigo_venta = codigo_venta;
        this.fecha_venta = fecha_venta;
        this.cliente = cliente;
        this.detalles = new ArrayList<>();
        this.total= calcularTotal();
    }

    public double calcularTotal(){

        double totalVenta = 0;

        for (DetalleVenta detalle : detalles){
               totalVenta+=detalle.getPrecio();
        }

        return totalVenta;
    }

    public void agregarDetalle(DetalleVenta detalle){
        detalle.setVenta(this);
        detalles.add(detalle);
    }


}
