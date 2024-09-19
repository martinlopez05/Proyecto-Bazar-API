package com.proyecto.ventas.proyectoventasbazar.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@Entity

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idCliente")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idCliente;


    private String nombre;
    private String apellido;
    private String dni;


    @OneToMany(mappedBy = "cliente")
    //@JsonManagedReference("cliente-venta")
    private List<Venta> ventas;


    public Cliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public Cliente() {
    }

    public Cliente(Long idCliente, String nombre, String apellido, String dni) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
    }

    public Cliente(String nombre, String apellido, Long id_cliente, List<Venta> ventas, String dni) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.idCliente = id_cliente;
        this.ventas = ventas;
        this.dni = dni;
    }




}
