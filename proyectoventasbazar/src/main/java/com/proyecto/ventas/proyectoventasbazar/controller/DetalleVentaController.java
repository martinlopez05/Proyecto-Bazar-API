package com.proyecto.ventas.proyectoventasbazar.controller;


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
    public List<DetalleVenta> traerDetallesVentas(){
        return detalleServ.getDetalles();
    }

    @GetMapping("/detalles/{id_detalle}")
    public DetalleVenta traerDetalleVenta(@PathVariable Long id_detalle){
        return detalleServ.findDetalle(id_detalle);
    }


    @DeleteMapping("/detalles/eliminar/{id_detalle}")
    public String eliminarDetalleVenta(@PathVariable Long id_detalle){
        detalleServ.deleteDetalle(id_detalle);
        return "Detalle eliminado correctamente";
    }

    @PutMapping("/detalles/editar/{id_detalle}")
    public DetalleVenta editarDetalleVenta(@PathVariable Long id_detalle, @RequestBody DetalleVenta detalle){
        DetalleVenta detalleEditar = detalleServ.findDetalle(id_detalle);
        detalleEditar.setCantidad(detalle.getCantidad());
        detalleEditar.setProducto(detalle.getProducto());
        detalleEditar.setVenta(detalle.getVenta());

        detalleServ.editDetalle(detalleEditar);
        return detalleServ.findDetalle(id_detalle);

    }

}
