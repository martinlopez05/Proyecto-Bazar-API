package com.proyecto.ventas.proyectoventasbazar.controller;

import com.proyecto.ventas.proyectoventasbazar.dto.VentaDTO;
import com.proyecto.ventas.proyectoventasbazar.exceptions.ExceptionUtils;
import com.proyecto.ventas.proyectoventasbazar.model.Producto;
import com.proyecto.ventas.proyectoventasbazar.model.Venta;
import com.proyecto.ventas.proyectoventasbazar.service.IVentaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/ventas")
public class VentaController {

    @Autowired
    IVentaService ventaServ;

    @GetMapping
    public List <VentaDTO> traerVentas(){
        return ventaServ.getVentas();
    }

    @GetMapping("/{codigoVenta}")
    public ResponseEntity<VentaDTO> traerVenta(@PathVariable Long codigoVenta){
        ExceptionUtils.validateId(codigoVenta);
        VentaDTO ventaDto = ventaServ.getVentaDTO(codigoVenta);
        return ResponseEntity.ok(ventaDto);
    }

    @GetMapping("/{codigoVenta}/productos")
    public List<Producto> traerProductosVenta(@PathVariable Long codigoVenta){
        ExceptionUtils.validateId(codigoVenta);
        return ventaServ.getProductosVenta(codigoVenta);
    }

    @GetMapping("/cliente/{idCliente}")
    public List<VentaDTO> traerVentasPorCliente(@PathVariable Long idCliente){
        ExceptionUtils.validateId(idCliente);
        return ventaServ.getVentasPorCliente(idCliente);
    }


    @PostMapping
    public ResponseEntity<VentaDTO> crearventa( @Valid @RequestBody VentaDTO venta){
        ventaServ.saveVenta(venta);
        return new ResponseEntity<>(venta, HttpStatus.CREATED);
    }

    @DeleteMapping("/{codigoVenta}")
    public ResponseEntity<?> eliminarVenta(@PathVariable Long codigoVenta){
        ExceptionUtils.validateId(codigoVenta);
        ventaServ.deleteVenta(codigoVenta);
        return new ResponseEntity<>("Venta eliminada correctamente", HttpStatus.ACCEPTED);
    }


    @PutMapping("/{codigoVenta}")
    public ResponseEntity<VentaDTO> editarVenta(@PathVariable Long codigoVenta,@RequestBody VentaDTO ventadto){
        ExceptionUtils.validateId(codigoVenta);
        ventaServ.editVenta(codigoVenta,ventadto);
        VentaDTO ventaEditada = ventaServ.getVentaDTO(codigoVenta);
        return ResponseEntity.ok(ventaEditada);
    }
}
