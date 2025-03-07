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

/**
 * Controlador REST para gestionar operaciones relacionadas con la entidad Cliente.
 * Proporciona endpoints para realizar operaciones CRUD sobre los clientes.
 */

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    IClienteService clienteServ;

    
    /**
     * Obtiene la lista completa de clientes almacenados en la base de datos.
     * 
     * @return Lista de objetos Cliente.
     */
    @GetMapping
    public List<Cliente> traerClientes() {
        List<Cliente> clientes = clienteServ.getClientes();
        return clientes;
    }

    /**
     * Obtiene un cliente específico según su identificador único.
     * 
     * @param idCliente Identificador único del cliente.
     * @return Cliente encontrado o un error 404 si no existe.
     */
    @GetMapping("/{idCliente}")
    public ResponseEntity<Cliente> traerCliente(@PathVariable Long idCliente){
        ExceptionUtils.validateId(idCliente);
        Cliente cliente = clienteServ.findCliente(idCliente);
        return ResponseEntity.ok(cliente);
    }
    
    
    /**
     * Crea un nuevo cliente en la base de datos.
     * 
     * @param cliente Objeto Cliente con los datos a almacenar.
     * @return Cliente creado con un estado HTTP 201 (CREATED).
     */
    @PostMapping
    public ResponseEntity<Cliente> crearCliente(@Valid @RequestBody Cliente cliente) {
        clienteServ.saveCliente(cliente);
        return new ResponseEntity(cliente, HttpStatus.CREATED);

    }

    /**
     * Elimina un cliente específico según su identificador único.
     * 
     * @param idCliente Identificador del cliente a eliminar.
     * @return Mensaje de éxito con estado HTTP 200 (OK).
     */
    @DeleteMapping("/{idCliente}")
    public ResponseEntity<?> eliminarCliente(@PathVariable Long idCliente){
        ExceptionUtils.validateId(idCliente);
        clienteServ.deleteCliente(idCliente);
        return new ResponseEntity<>("Cliente eliminado correctamente", HttpStatus.OK);
    }

    /**
     * Actualiza los datos de un cliente existente.
     * 
     * @param idCliente Identificador del cliente a actualizar.
     * @param cliente Objeto Cliente con los datos actualizados.
     * @return Cliente actualizado con estado HTTP 200 (OK).
     */
    @PutMapping("/{idCliente}")
    public ResponseEntity<Cliente> editarCliente(@PathVariable Long idCliente, @RequestBody Cliente cliente){
        ExceptionUtils.validateId(idCliente);
        clienteServ.editCliente(idCliente, cliente);
        Cliente clienteEditado = clienteServ.findCliente(idCliente);
        return  ResponseEntity.ok(clienteEditado);

    }

    

}
