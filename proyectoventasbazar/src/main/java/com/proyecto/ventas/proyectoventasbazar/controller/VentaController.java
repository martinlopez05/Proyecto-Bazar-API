package com.proyecto.ventas.proyectoventasbazar.controller;

import com.proyecto.ventas.proyectoventasbazar.dto.VentaDTO;
import com.proyecto.ventas.proyectoventasbazar.model.Producto;
import com.proyecto.ventas.proyectoventasbazar.model.Venta;
import com.proyecto.ventas.proyectoventasbazar.service.IVentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class VentaController {

    @Autowired
    IVentaService ventaServ;

    @GetMapping("/ventas")
    public List <Venta> traerClientes(){
        return ventaServ.getVentas();
    }

    @GetMapping("/ventas/{codigo_venta}")
    public Venta traerVenta(@PathVariable Long codigo_venta){
        return ventaServ.findVenta(codigo_venta);
    }

    @GetMapping("/ventas/productos/{codigo_venta}")
    public List<Producto> traerProductosVenta(@PathVariable Long codigo_venta){
        return ventaServ.getProductosVenta(codigo_venta);
    }

    @GetMapping("/ventas/{fecha_venta}")
    public String traerMontoyCantporFecha(@PathVariable LocalDate fecha_venta){
        return ventaServ.getMontoyCantidad(fecha_venta);
    }

    @GetMapping("/ventas/{id_cliente}")
    public List<Venta> traerVentasPorCliente(@PathVariable Long id_cliente){
        return ventaServ.getVentasPorCliente(id_cliente);
    }



    @PostMapping("/ventas/crear")
    public String crearventa(@RequestBody VentaDTO venta){
        ventaServ.saveVenta(venta);
        return "Venta creada correctamente";
    }

    @DeleteMapping("/ventas/eliminar/{codigo_venta}")
    public String eliminarVenta(@PathVariable Long codigo_venta){
        ventaServ.deleteVenta(codigo_venta);
        return "Venta eliminada correctamente";
    }


    @PutMapping("/ventas/editar/{codigo_venta}")
    public Venta editarVenta(@PathVariable Long codigo_venta,@RequestBody Venta venta){
        Venta ventaEditar = ventaServ.findVenta(codigo_venta);
        ventaEditar.setFecha_venta(venta.getFecha_venta());
        ventaEditar.setTotal(venta.getTotal());
        ventaEditar.setCliente(venta.getCliente());

        ventaServ.editVenta(ventaEditar);
        return ventaServ.findVenta(codigo_venta);

    }




}
