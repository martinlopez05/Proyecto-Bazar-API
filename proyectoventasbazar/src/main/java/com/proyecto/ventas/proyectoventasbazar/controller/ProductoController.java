package com.proyecto.ventas.proyectoventasbazar.controller;

import com.proyecto.ventas.proyectoventasbazar.dto.DetalleDTO;
import com.proyecto.ventas.proyectoventasbazar.exceptions.ExceptionUtils;
import com.proyecto.ventas.proyectoventasbazar.exceptions.InvalidArgumentException;
import com.proyecto.ventas.proyectoventasbazar.model.Cliente;
import com.proyecto.ventas.proyectoventasbazar.model.Producto;
import com.proyecto.ventas.proyectoventasbazar.service.IProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Controlador REST para gestionar operaciones relacionadas con la entidad Producto.
 * Proporciona endpoints para realizar operaciones CRUD sobre los productos.
 */

@Tag(name = "Producto Controller", description = "Operaciones relacionadas con productos")
@RestController
@RequestMapping("/productos")
public class ProductoController {
    @Autowired
    IProductoService producServ;


    @Operation(summary = "Obtener lista de productos", description = "Devuelve todos los productos disponibles")
    @ApiResponse(responseCode = "200", description = "Lista de productos",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Producto.class))))
    @GetMapping
    public List<Producto> traerProductos (){
        return producServ.getProductos();
    }


    @Operation(summary = "Obtener producto por código", description = "Busca un producto por su código")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Producto encontrado",
                    content = @Content(schema = @Schema(implementation = Producto.class))),
            @ApiResponse(responseCode = "400", description = "Código inválido", content = @Content),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado", content = @Content)
    })
    @GetMapping("/{codigoProducto}")
    public ResponseEntity<Producto> traerProducto(@PathVariable Long codigoProducto){
        ExceptionUtils.validateId(codigoProducto);
        Producto producto = producServ.findProducto(codigoProducto);
        return ResponseEntity.ok(producto);
    }


    @Operation(summary = "Productos con stock menor o igual a un número", description = "Filtra productos cuyo stock es menor o igual al número dado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de productos filtrados",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Producto.class)))),
            @ApiResponse(responseCode = "400", description = "Número de stock inválido", content = @Content)
    })
    @GetMapping("/stock-maximo/{numeroStock}")
    public List<Producto> traerProdStockMen5(@PathVariable int numeroStock){
        if(numeroStock<0){
            throw new InvalidArgumentException("el numero de stock debe ser mayor o igual a 0", "p-400");
        }
        return producServ.getStockMenorA(numeroStock);
    }



    @Operation(summary = "Detalles de venta por producto", description = "Obtiene los detalles de venta asociados a un producto")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de detalles de venta",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = DetalleDTO.class)))),
            @ApiResponse(responseCode = "400", description = "Código inválido", content = @Content)
    })
    @GetMapping("/{codigoProducto}/detalles")
    public List<DetalleDTO> traerDetallesporProducto( @PathVariable  Long codigoProducto){
        ExceptionUtils.validateId(codigoProducto);
        return producServ.getDetallesporProducto(codigoProducto);
    }


    @Operation(summary = "Crear nuevo producto", description = "Guarda un nuevo producto en la base de datos")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Producto creado", content = @Content(schema = @Schema(implementation = Producto.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content)
    })
    @PostMapping
    public ResponseEntity<Producto> crearProducto(@Valid @RequestBody Producto producto){
        producServ.saveProducto(producto);
        return new ResponseEntity<>(producto, HttpStatus.CREATED);
    }


    @Operation(summary = "Eliminar producto", description = "Elimina un producto por su código")
    @ApiResponses({
            @ApiResponse(responseCode = "202", description = "Producto eliminado correctamente", content = @Content),
            @ApiResponse(responseCode = "400", description = "Código inválido", content = @Content),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado", content = @Content)
    })
    @DeleteMapping("/{codigoProducto}")
    public ResponseEntity<?> eliminarProducto(@PathVariable Long codigoProducto){
        ExceptionUtils.validateId(codigoProducto);
        producServ.deleteProducto(codigoProducto);
        return new ResponseEntity<>("Producto eliminado correctamente", HttpStatus.ACCEPTED);
    }


    @Operation(summary = "Editar producto", description = "Edita un producto existente por su código")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Producto editado correctamente", content = @Content(schema = @Schema(implementation = Producto.class))),
            @ApiResponse(responseCode = "400", description = "Código inválido o datos incorrectos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado", content = @Content)
    })
    @PutMapping("/{codigoProducto}")
    public ResponseEntity<Producto> editarProducto(@PathVariable Long codigoProducto,@RequestBody Producto producto){
           ExceptionUtils.validateId(codigoProducto);
           producServ.editProducto(codigoProducto,producto);
           Producto productoEditado = (producServ.findProducto(codigoProducto));
           return ResponseEntity.ok(productoEditado);

    }
}
