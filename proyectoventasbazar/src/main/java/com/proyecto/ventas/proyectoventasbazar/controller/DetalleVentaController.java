package com.proyecto.ventas.proyectoventasbazar.controller;


import com.proyecto.ventas.proyectoventasbazar.dto.DetalleDTO;
import com.proyecto.ventas.proyectoventasbazar.exceptions.ExceptionUtils;
import com.proyecto.ventas.proyectoventasbazar.model.DetalleVenta;
import com.proyecto.ventas.proyectoventasbazar.service.DetalleVentaService;
import com.proyecto.ventas.proyectoventasbazar.service.IDetalleVentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@RestController
@RequestMapping("/detalles")
public class DetalleVentaController {

    @Autowired
    IDetalleVentaService detalleServ;


    public List<DetalleDTO> traerDetallesVentas(){
        return detalleServ.getDetalles();
    }

    @GetMapping("/{idDetalle}")
    public ResponseEntity<DetalleVenta> traerDetalleVenta(@PathVariable Long idDetalle){
        ExceptionUtils.validateId(idDetalle);
        DetalleVenta detalle = detalleServ.findDetalle(idDetalle);
        return ResponseEntity.ok(detalle);
    }


    @DeleteMapping("/{idDetalle}")
    public ResponseEntity<?> eliminarDetalleVenta(@PathVariable Long idDetalle){
        ExceptionUtils.validateId(idDetalle);
        detalleServ.deleteDetalle(idDetalle);
        return new ResponseEntity<>("Detalle eliminado correctamente",  HttpStatus.ACCEPTED);
    }


    @PutMapping("/{idDetalle}")
    public ResponseEntity<DetalleVenta> editarDetalleVenta(@PathVariable Long idDetalle, @RequestBody DetalleDTO detalledto){
        ExceptionUtils.validateId(idDetalle);
        detalleServ.editDetalle(idDetalle,detalledto);
        DetalleVenta detalleEditado = detalleServ.findDetalle(idDetalle);
        return ResponseEntity.ok(detalleEditado);
    }

}
