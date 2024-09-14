package com.proyecto.ventas.proyectoventasbazar.controller;


import com.proyecto.ventas.proyectoventasbazar.model.Cliente;
import com.proyecto.ventas.proyectoventasbazar.service.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class Clientecontroller {

    @Autowired
    IClienteService clienteServ;

    @GetMapping("/clientes")
    public List<Cliente>  traerClientes(){
        return clienteServ.getClientes();
    }

    @GetMapping("/clientes/{idCliente}")
    public Cliente traerCliente(@PathVariable Long idCliente){
        return clienteServ.findCliente(idCliente);
    }

    @PostMapping("/clientes/crear")
    public String crearCliente(@RequestBody Cliente cliente){
        clienteServ.saveCliente(cliente);
        return "Cliente creado correctamente";

    }

    @DeleteMapping("/clientes/eliminar/{idCliente}")
    public String eliminarCliente(@PathVariable Long idCliente){
        clienteServ.deleteCliente(idCliente);
        return "Cliente eliminado correctamente";
    }


    @PutMapping("/clientes/editar/{idCliente}")
    public Cliente editarCliente(@PathVariable Long idCliente,@RequestBody Cliente cliente){
           clienteServ.editCliente(idCliente,cliente);
           return clienteServ.findCliente(idCliente);

    }





}
