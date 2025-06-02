package com.proyecto.ventas.proyectoventasbazar.controller;

import com.proyecto.ventas.proyectoventasbazar.dto.DetalleDTO;
import com.proyecto.ventas.proyectoventasbazar.exceptions.ExceptionUtils;
import com.proyecto.ventas.proyectoventasbazar.exceptions.InvalidArgumentException;
import com.proyecto.ventas.proyectoventasbazar.model.Cliente;
import com.proyecto.ventas.proyectoventasbazar.model.Producto;
import com.proyecto.ventas.proyectoventasbazar.service.IProductoService;
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

    /**
     * Obtiene la lista completa de productos almacenados en la base de datos.
     * 
     * @return Lista de objetos Producto.
     */
    @GetMapping
    public List<Producto> traerProductos (){
        return producServ.getProductos();
    }
    
    /**
     * Obtiene un producto específico según su código.
     * 
     * @param codigoProducto Código único del producto.
     * @return Producto encontrado o un error 404 si no existe.
     */
    @GetMapping("/{codigoProducto}")
    public ResponseEntity<Producto> traerProducto(@PathVariable Long codigoProducto){
        ExceptionUtils.validateId(codigoProducto);
        Producto producto = producServ.findProducto(codigoProducto);
        return ResponseEntity.ok(producto);
    }
    
    /**
     * Obtiene la lista de productos cuyo stock es menor o igual a un número dado.
     * 
     * @param numeroStock Límite máximo de stock.
     * @return Lista de productos con stock menor o igual al número especificado.
     */
    @GetMapping("/stock-maximo/{numeroStock}")
    public List<Producto> traerProdStockMen5(@PathVariable int numeroStock){
        if(numeroStock<0){
            throw new InvalidArgumentException("el numero de stock debe ser mayor o igual a 0", "p-400");
        }
        return producServ.getStockMenorA(numeroStock);
    }

    /**
     * Obtiene la lista de detalles asociados a un producto específico.
     * 
     * @param codigoProducto Código único del producto.
     * @return Lista de detalles asociados al producto.
     */
    @GetMapping("/{codigoProducto}/detalles")
    public List<DetalleDTO> traerDetallesporProducto( @PathVariable  Long codigoProducto){
        ExceptionUtils.validateId(codigoProducto);
        return producServ.getDetallesporProducto(codigoProducto);
    }

    
    /**
     * Crea un nuevo producto en la base de datos.
     * 
     * @param producto Objeto Producto a ser creado.
     * @return Producto creado con estado HTTP 201 (CREATED).
     */
    @PostMapping
    public ResponseEntity<Producto> crearProducto(@Valid @RequestBody Producto producto){
        producServ.saveProducto(producto);
        return new ResponseEntity<>(producto, HttpStatus.CREATED);
    }

    /**
     * Elimina un producto de la base de datos según su código.
     * 
     * @param codigoProducto Código único del producto a eliminar.
     * @return Mensaje de éxito con estado HTTP 202 (ACCEPTED).
     */
    @DeleteMapping("/{codigoProducto}")
    public ResponseEntity<?> eliminarProducto(@PathVariable Long codigoProducto){
        ExceptionUtils.validateId(codigoProducto);
        producServ.deleteProducto(codigoProducto);
        return new ResponseEntity<>("Producto eliminado correctamente", HttpStatus.ACCEPTED);
    }

    /**
     * Actualiza los datos de un producto existente.
     * 
     * @param codigoProducto Código único del producto a actualizar.
     * @param producto Objeto Producto con los datos actualizados.
     * @return Producto actualizado con estado HTTP 200 (OK).
     */
    @PutMapping("/{codigoProducto}")
    public ResponseEntity<Producto> editarProducto(@PathVariable Long codigoProducto,@RequestBody Producto producto){
           ExceptionUtils.validateId(codigoProducto);
           producServ.editProducto(codigoProducto,producto);
           Producto productoEditado = (producServ.findProducto(codigoProducto));
           return ResponseEntity.ok(productoEditado);

    }
}
