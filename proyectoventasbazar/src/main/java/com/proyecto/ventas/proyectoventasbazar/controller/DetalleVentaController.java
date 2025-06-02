package com.proyecto.ventas.proyectoventasbazar.controller;


import com.proyecto.ventas.proyectoventasbazar.dto.DetalleDTO;
import com.proyecto.ventas.proyectoventasbazar.exceptions.ExceptionUtils;
import com.proyecto.ventas.proyectoventasbazar.model.DetalleVenta;
import com.proyecto.ventas.proyectoventasbazar.service.DetalleVentaService;
import com.proyecto.ventas.proyectoventasbazar.service.IDetalleVentaService;
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

    /**
     * Obtiene la lista completa de detalles de ventas almacenados en la base de datos.
     * 
     * @return Lista de objetos DetalleDTO.
     */
    public List<DetalleDTO> traerDetallesVentas(){
        return detalleServ.getDetalles();
    }

    /**
     * Obtiene un detalle de venta específico según su identificador único.
     * 
     * @param idDetalle Identificador único del detalle de venta.
     * @return Detalle de venta encontrado o un error 404 si no existe.
     */
    @GetMapping("/{idDetalle}")
    public ResponseEntity<DetalleVenta> traerDetalleVenta(@PathVariable Long idDetalle){
        ExceptionUtils.validateId(idDetalle);
        DetalleVenta detalle = detalleServ.findDetalle(idDetalle);
        return ResponseEntity.ok(detalle);
    }

    /**
     * Elimina un detalle de venta específico según su identificador único.
     * 
     * @param idDetalle Identificador del detalle de venta a eliminar.
     * @return Mensaje de éxito con estado HTTP 202 (ACCEPTED).
     */
    @DeleteMapping("/{idDetalle}")
    public ResponseEntity<?> eliminarDetalleVenta(@PathVariable Long idDetalle){
        ExceptionUtils.validateId(idDetalle);
        detalleServ.deleteDetalle(idDetalle);
        return new ResponseEntity<>("Detalle eliminado correctamente",  HttpStatus.ACCEPTED);
    }

    /**
     * Actualiza los datos de un detalle de venta existente.
     * 
     * @param idDetalle Identificador del detalle de venta a actualizar.
     * @param detalledto Objeto DetalleDTO con los datos actualizados.
     * @return Detalle de venta actualizado con estado HTTP 200 (OK).
     */
    @PutMapping("/{idDetalle}")
    public ResponseEntity<DetalleVenta> editarDetalleVenta(@PathVariable Long idDetalle, @RequestBody DetalleDTO detalledto){
        ExceptionUtils.validateId(idDetalle);
        detalleServ.editDetalle(idDetalle,detalledto);
        DetalleVenta detalleEditado = detalleServ.findDetalle(idDetalle);
        return ResponseEntity.ok(detalleEditado);
    }

}
