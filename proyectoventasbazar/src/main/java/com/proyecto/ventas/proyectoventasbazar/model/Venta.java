package com.proyecto.ventas.proyectoventasbazar.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Entity

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "codigoVenta")
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long codigoVenta;
    private LocalDate fechaVenta;
    private Double total;


    @ManyToOne(optional = false)
    @JoinColumn(name = "id_cliente")
    @JsonBackReference("cliente-venta")
    private Cliente cliente;


    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL ,orphanRemoval = true)
    @JsonManagedReference("venta-detalle")
    private List<DetalleVenta> detalles = new ArrayList<>();

    public Venta() {
    }

    public Venta(Long codigoVenta, LocalDate fechaVenta,Cliente cliente) {
        this.codigoVenta = codigoVenta;
        this.fechaVenta = fechaVenta;
        this.cliente = cliente;
        this.detalles = new ArrayList<>();
    }



    @PrePersist
    @PreUpdate
    public void calcularTotal(){

        double totalVenta = 0;

        for (DetalleVenta detalle : detalles){
               totalVenta+=detalle.getPrecio();
        }

        this.total = totalVenta;
    }

    public void agregarDetalle(DetalleVenta detalle){
        detalle.setVenta(this);
        detalles.add(detalle);
    }


}
