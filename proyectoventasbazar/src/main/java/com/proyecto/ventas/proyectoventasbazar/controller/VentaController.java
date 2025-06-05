package com.proyecto.ventas.proyectoventasbazar.controller;

import com.proyecto.ventas.proyectoventasbazar.dto.VentaDTO;
import com.proyecto.ventas.proyectoventasbazar.exceptions.ExceptionUtils;
import com.proyecto.ventas.proyectoventasbazar.model.Producto;
import com.proyecto.ventas.proyectoventasbazar.model.Venta;
import com.proyecto.ventas.proyectoventasbazar.service.IVentaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "Venta Controller", description = "Operaciones relacionadas con ventas")
@RestController
@RequestMapping("/ventas")
public class VentaController {

    @Autowired
    IVentaService ventaServ;


    @Operation(summary = "Obtener lista de ventas", description = "Devuelve todas las ventas registradas")
    @ApiResponse(responseCode = "200", description = "Lista de ventas",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = VentaDTO.class))))
    @GetMapping
    public List <VentaDTO> traerVentas(){
        return ventaServ.getVentas();
    }


    @Operation(summary = "Obtener venta por código", description = "Busca una venta por su código")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Venta encontrada",
                    content = @Content(schema = @Schema(implementation = VentaDTO.class))),
            @ApiResponse(responseCode = "400", description = "Código inválido", content = @Content),
            @ApiResponse(responseCode = "404", description = "Venta no encontrada", content = @Content)
    })
    @GetMapping("/{codigoVenta}")
    public ResponseEntity<VentaDTO> traerVenta(@PathVariable Long codigoVenta){
        ExceptionUtils.validateId(codigoVenta);
        VentaDTO ventaDto = ventaServ.getVentaDTO(codigoVenta);
        return ResponseEntity.ok(ventaDto);
    }


    @Operation(summary = "Obtener productos de una venta", description = "Lista los productos asociados a una venta")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de productos de la venta",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Producto.class)))),
            @ApiResponse(responseCode = "400", description = "Código inválido", content = @Content)
    })
    @GetMapping("/{codigoVenta}/productos")
    public List<Producto> traerProductosVenta(@PathVariable Long codigoVenta){
        ExceptionUtils.validateId(codigoVenta);
        return ventaServ.getProductosVenta(codigoVenta);
    }


    @Operation(summary = "Obtener ventas por cliente", description = "Lista todas las ventas realizadas por un cliente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de ventas por cliente",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = VentaDTO.class)))),
            @ApiResponse(responseCode = "400", description = "ID de cliente inválido", content = @Content)
    })
    @GetMapping("/cliente/{idCliente}")
    public List<VentaDTO> traerVentasPorCliente(@PathVariable Long idCliente){
        ExceptionUtils.validateId(idCliente);
        return ventaServ.getVentasPorCliente(idCliente);
    }


    @Operation(summary = "Obtener ventas por fecha", description = "Lista ventas realizadas en una fecha específica")
    @ApiResponse(responseCode = "200", description = "Lista de ventas filtradas por fecha",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = VentaDTO.class))))
    @GetMapping("/fecha/{fecha}")
    public List<VentaDTO> traerVentasPorFecha(@PathVariable LocalDate fecha){
        return ventaServ.getVentasPorFecha(fecha);
    }



    @Operation(summary = "Crear nueva venta", description = "Registra una nueva venta")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Venta creada correctamente",
                    content = @Content(schema = @Schema(implementation = VentaDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content)
    })
    @PostMapping
    public ResponseEntity<VentaDTO> crearVenta( @Valid @RequestBody VentaDTO ventaDto){
        VentaDTO venta = ventaServ.saveVenta(ventaDto);
        return new ResponseEntity<>(venta, HttpStatus.CREATED);
    }


    @Operation(summary = "Eliminar venta", description = "Elimina una venta por su código")
    @ApiResponses({
            @ApiResponse(responseCode = "202", description = "Venta eliminada correctamente", content = @Content),
            @ApiResponse(responseCode = "400", description = "Código inválido", content = @Content),
            @ApiResponse(responseCode = "404", description = "Venta no encontrada", content = @Content)
    })
    @DeleteMapping("/{codigoVenta}")
    public ResponseEntity<?> eliminarVenta(@PathVariable Long codigoVenta){
        ExceptionUtils.validateId(codigoVenta);
        ventaServ.deleteVenta(codigoVenta);
        return new ResponseEntity<>("Venta eliminada correctamente", HttpStatus.ACCEPTED);
    }

}
