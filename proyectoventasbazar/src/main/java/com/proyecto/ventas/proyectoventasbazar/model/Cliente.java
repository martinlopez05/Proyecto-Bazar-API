package com.proyecto.ventas.proyectoventasbazar.model;


import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@Entity


public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long idCliente;

    @NotBlank( message = "El nombre no puede estar en blanco")
    private String nombre;
    
    @NotBlank(message = "El apellido no puede estar en blanco")
    private String apellido;
    
    @NotBlank( message = "El dni no puede estar en blanco")
    private String dni;


    @OneToMany(mappedBy = "cliente")
    @JsonIgnore
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
