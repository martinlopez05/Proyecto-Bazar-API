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
    public List <VentaDTO> traerVentas(){
        return ventaServ.getVentas();
    }

    @GetMapping("/ventas/{codigoVenta}")
    public VentaDTO traerVenta(@PathVariable Long codigoVenta){
        return ventaServ.getVentaDTO(codigoVenta);
    }

    @GetMapping("/ventas/productos/{codigoVenta}")
    public List<Producto> traerProductosVenta(@PathVariable Long codigoVenta){
        return ventaServ.getProductosVenta(codigoVenta);
    }



    @GetMapping("/ventas/cliente/{idCliente}")
    public List<VentaDTO> traerVentasPorCliente(@PathVariable Long idCliente){
        return ventaServ.getVentasPorCliente(idCliente);
    }



    @PostMapping("/ventas/crear")
    public String crearventa(@RequestBody VentaDTO venta){
        ventaServ.saveVenta(venta);
        return "Venta creada correctamente";
    }

    @DeleteMapping("/ventas/eliminar/{codigoVenta}")
    public String eliminarVenta(@PathVariable Long codigoVenta){
        ventaServ.deleteVenta(codigoVenta);
        return "Venta eliminada correctamente";
    }


    @PutMapping("/ventas/editar/{codigoVenta}")
    public VentaDTO editarVenta(@PathVariable Long codigoVenta,@RequestBody VentaDTO ventadto){
        ventaServ.editVenta(codigoVenta,ventadto);
        return ventaServ.getVentaDTO(codigoVenta);
    }




}
