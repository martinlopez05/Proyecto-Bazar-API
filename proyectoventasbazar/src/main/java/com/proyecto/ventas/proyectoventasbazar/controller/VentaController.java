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

/**
 * Controlador REST para gestionar operaciones relacionadas con la entidad Venta.
 * Proporciona endpoints para realizar operaciones CRUD sobre las ventas.
 */

@RestController
@RequestMapping("/ventas")
public class VentaController {

    @Autowired
    IVentaService ventaServ;

    /**
     * Obtiene la lista completa de ventas almacenadas en la base de datos.
     * 
     * @return Lista de objetos VentaDTO.
     */
    @GetMapping
    public List <VentaDTO> traerVentas(){
        return ventaServ.getVentas();
    }

    /**
     * Obtiene una venta específica según su identificador único.
     * 
     * @param codigoVenta Identificador único de la venta.
     * @return Venta encontrada o un error 404 si no existe.
     */
    @GetMapping("/{codigoVenta}")
    public ResponseEntity<VentaDTO> traerVenta(@PathVariable Long codigoVenta){
        ExceptionUtils.validateId(codigoVenta);
        VentaDTO ventaDto = ventaServ.getVentaDTO(codigoVenta);
        return ResponseEntity.ok(ventaDto);
    }

    
    /**
     * Obtiene la lista de productos asociados a una venta específica.
     * 
     * @param codigoVenta Identificador de la venta.
     * @return Lista de productos de la venta.
     */
    @GetMapping("/{codigoVenta}/productos")
    public List<Producto> traerProductosVenta(@PathVariable Long codigoVenta){
        ExceptionUtils.validateId(codigoVenta);
        return ventaServ.getProductosVenta(codigoVenta);
    }

    /**
     * Obtiene la lista de ventas asociadas a un cliente específico.
     * 
     * @param idCliente Identificador del cliente.
     * @return Lista de ventas del cliente.
     */
    @GetMapping("/cliente/{idCliente}")
    public List<VentaDTO> traerVentasPorCliente(@PathVariable Long idCliente){
        ExceptionUtils.validateId(idCliente);
        return ventaServ.getVentasPorCliente(idCliente);
    }

    /**
     * Crea una nueva venta en la base de datos.
     * 
     * @param venta Objeto VentaDTO con los datos de la nueva venta.
     * @return Venta creada con estado HTTP 201 (CREATED).
     */
    @PostMapping
    public ResponseEntity<VentaDTO> crearventa( @Valid @RequestBody VentaDTO venta){
        ventaServ.saveVenta(venta);
        return new ResponseEntity<>(venta, HttpStatus.CREATED);
    }
    
    /**
     * Elimina una venta específica según su identificador único.
     * 
     * @param codigoVenta Identificador de la venta a eliminar.
     * @return Mensaje de éxito con estado HTTP 202 (ACCEPTED).
     */
    @DeleteMapping("/{codigoVenta}")
    public ResponseEntity<?> eliminarVenta(@PathVariable Long codigoVenta){
        ExceptionUtils.validateId(codigoVenta);
        ventaServ.deleteVenta(codigoVenta);
        return new ResponseEntity<>("Venta eliminada correctamente", HttpStatus.ACCEPTED);
    }

    /**
     * Actualiza los datos de una venta existente.
     * 
     * @param codigoVenta Identificador de la venta a actualizar.
     * @param ventadto Objeto VentaDTO con los datos actualizados.
     * @return Venta actualizada con estado HTTP 200 (OK).
     */
    @PutMapping("/{codigoVenta}")
    public ResponseEntity<VentaDTO> editarVenta(@PathVariable Long codigoVenta,@RequestBody VentaDTO ventadto){
        ExceptionUtils.validateId(codigoVenta);
        ventaServ.editVenta(codigoVenta,ventadto);
        VentaDTO ventaEditada = ventaServ.getVentaDTO(codigoVenta);
        return ResponseEntity.ok(ventaEditada);
    }
}
