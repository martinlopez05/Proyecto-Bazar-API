package com.proyecto.ventas.proyectoventasbazar.model;

import com.fasterxml.jackson.annotation.*;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long codigoVenta;
    private LocalDate fechaVenta;
    private Double total;


    @ManyToOne(optional = false)
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;


    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL ,orphanRemoval = true)
    private List<DetalleVenta> detalles = new ArrayList<>();

    public Venta() {
        this.fechaVenta = LocalDate.now();
    }

    public Venta(Long codigoVenta,Cliente cliente) {
        this.codigoVenta = codigoVenta;
        this.fechaVenta = LocalDate.now();
        this.cliente = cliente;
        this.detalles = new ArrayList<>();
    }



    @PrePersist
    @PreUpdate
    public void calcularTotal() {
        double totalVenta = 0;

        for (DetalleVenta detalle : detalles) {
            totalVenta += detalle.getPrecio() * detalle.getCantidad();
        }

        this.total = totalVenta;
    }

    public void agregarDetalle(DetalleVenta detalle){
        detalle.setVenta(this);
        detalles.add(detalle);
    }


}
