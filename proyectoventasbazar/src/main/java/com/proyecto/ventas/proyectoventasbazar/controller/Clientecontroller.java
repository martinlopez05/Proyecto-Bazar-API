package com.proyecto.ventas.proyectoventasbazar.controller;

import com.proyecto.ventas.proyectoventasbazar.exceptions.ExceptionUtils;
import com.proyecto.ventas.proyectoventasbazar.model.Cliente;
import com.proyecto.ventas.proyectoventasbazar.service.IClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    IClienteService clienteServ;

    @GetMapping
    public List<Cliente> traerClientes() {
        List<Cliente> clientes = clienteServ.getClientes();
        return clientes;
    }

    @GetMapping("/{idCliente}")
    public ResponseEntity<Cliente> traerCliente(@PathVariable Long idCliente){
        ExceptionUtils.validateId(idCliente);
        Cliente cliente = clienteServ.findCliente(idCliente);
        return ResponseEntity.ok(cliente);
    }

    @PostMapping
    public ResponseEntity<Cliente> crearCliente(@Valid @RequestBody Cliente cliente) {
        clienteServ.saveCliente(cliente);
        return new ResponseEntity(cliente, HttpStatus.CREATED);

    }

    @DeleteMapping("/{idCliente}")
    public ResponseEntity<?> eliminarCliente(@PathVariable Long idCliente){
        ExceptionUtils.validateId(idCliente);
        clienteServ.deleteCliente(idCliente);
        return new ResponseEntity<>("Cliente eliminado correctamente", HttpStatus.OK);
    }

    @PutMapping("/{idCliente}")
    public ResponseEntity<Cliente> editarCliente(@PathVariable Long idCliente, @RequestBody Cliente cliente){
        ExceptionUtils.validateId(idCliente);
        clienteServ.editCliente(idCliente, cliente);
        Cliente clienteEditado = clienteServ.findCliente(idCliente);
        return  ResponseEntity.ok(clienteEditado);

    }

    

}
