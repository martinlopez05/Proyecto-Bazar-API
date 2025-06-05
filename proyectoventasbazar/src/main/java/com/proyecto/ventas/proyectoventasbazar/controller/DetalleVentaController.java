package com.proyecto.ventas.proyectoventasbazar.controller;


import com.proyecto.ventas.proyectoventasbazar.dto.DetalleDTO;
import com.proyecto.ventas.proyectoventasbazar.exceptions.ExceptionUtils;
import com.proyecto.ventas.proyectoventasbazar.model.DetalleVenta;
import com.proyecto.ventas.proyectoventasbazar.service.DetalleVentaService;
import com.proyecto.ventas.proyectoventasbazar.service.IDetalleVentaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Controlador REST para gestionar operaciones relacionadas con la entidad DetalleVenta.
 * Proporciona endpoints para realizar operaciones CRUD sobre los detalles de venta.
 */

@Tag(name = "DetalleVenta Controller", description = "Operaciones relacionadas con detalles de una venta")
@RestController
@RequestMapping("/detalles")
public class DetalleVentaController {

    @Autowired
    IDetalleVentaService detalleServ;



    @Operation(
            summary = "Obtener detalle de venta por ID",
            description = "Devuelve el detalle de venta correspondiente al ID proporcionado",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Detalle de venta encontrado",
                            content = @Content(schema = @Schema(implementation = DetalleVenta.class))),
                    @ApiResponse(responseCode = "400", description = "ID inválido"),
                    @ApiResponse(responseCode = "404", description = "Detalle no encontrado")
            }
    )
    @GetMapping("/{idDetalle}")
    public ResponseEntity<DetalleVenta> traerDetalleVenta(@PathVariable Long idDetalle){
        ExceptionUtils.validateId(idDetalle);
        DetalleVenta detalle = detalleServ.findDetalle(idDetalle);
        return ResponseEntity.ok(detalle);
    }

    @Operation(
            summary = "Eliminar detalle de venta por ID",
            description = "Elimina el detalle de venta con el ID especificado",
            responses = {
                    @ApiResponse(responseCode = "202", description = "Detalle eliminado correctamente"),
                    @ApiResponse(responseCode = "400", description = "ID inválido"),
                    @ApiResponse(responseCode = "404", description = "Detalle no encontrado")
            }
    )
    @DeleteMapping("/{idDetalle}")
    public ResponseEntity<?> eliminarDetalleVenta(@PathVariable Long idDetalle){
        ExceptionUtils.validateId(idDetalle);
        detalleServ.deleteDetalle(idDetalle);
        return new ResponseEntity<>("Detalle eliminado correctamente",  HttpStatus.ACCEPTED);
    }


    @Operation(
            summary = "Editar detalle de venta",
            description = "Actualiza el detalle de venta con los datos enviados en el DTO",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Detalle actualizado",
                            content = @Content(schema = @Schema(implementation = DetalleVenta.class))),
                    @ApiResponse(responseCode = "400", description = "ID inválido o datos inválidos"),
                    @ApiResponse(responseCode = "404", description = "Detalle no encontrado")
            }
    )
    @PutMapping("/{idDetalle}")
    public ResponseEntity<DetalleVenta> editarDetalleVenta(@PathVariable Long idDetalle, @RequestBody DetalleDTO detalledto){
        ExceptionUtils.validateId(idDetalle);
        detalleServ.editDetalle(idDetalle,detalledto);
        DetalleVenta detalleEditado = detalleServ.findDetalle(idDetalle);
        return ResponseEntity.ok(detalleEditado);
    }

}
