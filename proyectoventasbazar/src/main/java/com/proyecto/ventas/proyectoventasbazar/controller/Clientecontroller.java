package com.proyecto.ventas.proyectoventasbazar.controller;


import com.proyecto.ventas.proyectoventasbazar.service.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Clientecontroller {

    @Autowired
    IClienteService clienteServ;



}
