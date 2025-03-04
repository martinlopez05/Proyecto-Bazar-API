package com.proyecto.ventas.proyectoventasbazar.controller;

import com.proyecto.ventas.proyectoventasbazar.exceptions.InvalidArgumentException;
import com.proyecto.ventas.proyectoventasbazar.exceptions.ResourceNotFoundException;
import com.proyecto.ventas.proyectoventasbazar.model.Cliente;
import com.proyecto.ventas.proyectoventasbazar.service.IClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/clientes")
public class Clientecontroller {

    @Autowired
    IClienteService clienteServ;

    @GetMapping
    public List<Cliente> traerClientes() {
        List<Cliente> clientes = clienteServ.getClientes();
        if (clientes == null || clientes.isEmpty()) {
            throw new ResourceNotFoundException("No hay clientes", "P-400");
        }
        return clientes;

    }

    @GetMapping("/{idCliente}")
    public Cliente traerCliente(@PathVariable Long idCliente) throws InvalidArgumentException, ResourceNotFoundException {
        validarIdCliente(idCliente);
        return clienteServ.findCliente(idCliente);
    }

    @PostMapping("/crear")
    public String crearCliente(@Valid @RequestBody Cliente cliente) {
        clienteServ.saveCliente(cliente);
        return "Cliente creado correctamente";

    }

    @DeleteMapping("/eliminar/{idCliente}")
    public String eliminarCliente(@PathVariable Long idCliente) throws InvalidArgumentException {
        validarIdCliente(idCliente);
        clienteServ.deleteCliente(idCliente);
        return "Cliente eliminado correctamente";
    }

    @PutMapping("/editar/{idCliente}")
    public Cliente editarCliente(@PathVariable Long idCliente, @RequestBody Cliente cliente) throws InvalidArgumentException {
        validarIdCliente(idCliente);
        clienteServ.editCliente(idCliente, cliente);
        return clienteServ.findCliente(idCliente);

    }

    private void validarIdCliente(Long idCliente) throws InvalidArgumentException {
        if (idCliente == 0) {
            throw new InvalidArgumentException("Por favor ingresa una id válida", "P-400");
        }
    }

}
