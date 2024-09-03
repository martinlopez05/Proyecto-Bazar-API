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

    @GetMapping("/clientes/{id_cliente")
    public Cliente traerCliente(@PathVariable Long id_cliente){
        return clienteServ.findCliente(id_cliente);
    }

    @PostMapping("/clientes/crear")
    public String crearCliente(@RequestBody Cliente cliente){
        clienteServ.saveCliente(cliente);
        return "Cliente creado correctamente";

    }

    @DeleteMapping("/clientes/eliminar/{id_cliente}")
    public String eliminarCliente(@PathVariable Long id_cliente){
        clienteServ.deleteCliente(id_cliente);
        return "Cliente eliminado correctamente";
    }


    @PutMapping("/clientes/eidtar/{id_cliente}")
    public Cliente editarCliente(@PathVariable Long id_cliente,@RequestBody Cliente cliente){
        Cliente clienteEditar = clienteServ.findCliente(id_cliente);
        clienteEditar.setNombre(cliente.getNombre());
        clienteEditar.setApellido(cliente.getApellido());
        clienteEditar.setVentas(cliente.getVentas());
        clienteEditar.setDni(cliente.getDni());


        clienteServ.editCliente(clienteEditar);
        return clienteServ.findCliente(clienteEditar.getId_cliente());

    }





}
