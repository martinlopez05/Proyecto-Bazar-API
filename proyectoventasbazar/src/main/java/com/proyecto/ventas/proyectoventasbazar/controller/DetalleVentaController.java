package com.proyecto.ventas.proyectoventasbazar.controller;


import com.proyecto.ventas.proyectoventasbazar.dto.DetalleDTO;
import com.proyecto.ventas.proyectoventasbazar.model.DetalleVenta;
import com.proyecto.ventas.proyectoventasbazar.service.DetalleVentaService;
import com.proyecto.ventas.proyectoventasbazar.service.IDetalleVentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class DetalleVentaController {

    @Autowired
    IDetalleVentaService detalleServ;


    @GetMapping("/detalles")
    public List<DetalleDTO> traerDetallesVentas(){
        return detalleServ.getDetalles();
    }

    @GetMapping("/detalles/{idDetalle}")
    public DetalleDTO traerDetalleVenta(@PathVariable Long idDetalle){
        return detalleServ.getDetalleDTO(idDetalle);
    }


    @DeleteMapping("/detalles/eliminar/{idDetalle}")
    public String eliminarDetalleVenta(@PathVariable Long idDetalle){
        detalleServ.deleteDetalle(idDetalle);
        return "Detalle eliminado correctamente";
    }


    @PutMapping("/detalles/editar/{idDetalle}")
    public DetalleVenta editarDetalleVenta(@PathVariable Long idDetalle, @RequestBody DetalleDTO detalledto){
        detalleServ.editDetalle(idDetalle,detalledto);
        return detalleServ.findDetalle(idDetalle);
    }

}
