package com.proyecto.ventas.proyectoventasbazar.controller;

import com.proyecto.ventas.proyectoventasbazar.dto.ErrorDTO;
import com.proyecto.ventas.proyectoventasbazar.exceptions.ExceptionUtils;
import com.proyecto.ventas.proyectoventasbazar.model.Cliente;
import com.proyecto.ventas.proyectoventasbazar.service.IClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "Cliente Controller", description = "Operaciones relacionadas con clientes")
@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    IClienteService clienteServ;

    

    @GetMapping
    @Operation(
            summary = "Obtener todos los clientes",
            description = "Devuelve la lista completa de clientes almacenados en la base de datos.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Lista de clientes obtenida exitosamente",
                            content = @Content(schema = @Schema(implementation = Cliente.class))
                    )
            }
    )
    public List<Cliente> traerClientes() {
        List<Cliente> clientes = clienteServ.getClientes();
        return clientes;
    }


    @GetMapping("/{idCliente}")
    @Operation(summary = "Obtiene un cliente por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = Cliente.class))),
            @ApiResponse(responseCode = "404", description = "No encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
    })
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
    @Operation(
            summary = "Eliminar cliente",
            description = "Elimina un cliente específico según su identificador único.",
            parameters = {
                    @Parameter(name = "idCliente", description = "ID del cliente a eliminar", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Cliente eliminado correctamente"),
                    @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
            }
    )
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
