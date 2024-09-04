package com.proyecto.ventas.proyectoventasbazar.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@Entity
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_cliente;


    private String nombre;
    private String apellido;
    private String dni;


    @OneToMany(mappedBy = "cliente")
    @JsonManagedReference
    private List<Venta> ventas;

    public Cliente(Long id_cliente) {
        this.id_cliente = id_cliente;
    }

    public Cliente() {
    }

    public Cliente(Long id_cliente, String nombre, String apellido, String dni) {
        this.id_cliente = id_cliente;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
    }

    public Cliente(String nombre, String apellido, Long id_cliente, List<Venta> ventas, String dni) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.id_cliente = id_cliente;
        this.ventas = ventas;
        this.dni = dni;
    }




}
