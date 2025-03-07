package com.proyecto.ventas.proyectoventasbazar.controller;

import com.proyecto.ventas.proyectoventasbazar.dto.DetalleDTO;
import com.proyecto.ventas.proyectoventasbazar.exceptions.ExceptionUtils;
import com.proyecto.ventas.proyectoventasbazar.exceptions.InvalidArgumentException;
import com.proyecto.ventas.proyectoventasbazar.model.Cliente;
import com.proyecto.ventas.proyectoventasbazar.model.Producto;
import com.proyecto.ventas.proyectoventasbazar.service.IProductoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/productos")
public class ProductoController {
    @Autowired
    IProductoService producServ;

    @GetMapping
    public List<Producto> traerProductos (){
        return producServ.getProductos();
    }

    @GetMapping("/{codigoProducto}")
    public ResponseEntity<Producto> traerProducto(@PathVariable Long codigoProducto){
        ExceptionUtils.validateId(codigoProducto);
        Producto producto = producServ.findProducto(codigoProducto);
        return ResponseEntity.ok(producto);
    }

    @GetMapping("/stock-maximo/{numeroStock}")
    public List<Producto> traerProdStockMen5(@PathVariable int numeroStock){
        if(numeroStock<0){
            throw new InvalidArgumentException("el numero de stock debe ser mayor o igual a 0", "p-400");
        }
        return producServ.getStockMenorA(numeroStock);
    }

    @GetMapping("/detalles/{codigoProducto}")
    public List<DetalleDTO> traerDetallesporProducto( @PathVariable  Long codigoProducto){
        ExceptionUtils.validateId(codigoProducto);
        return producServ.getDetallesporProducto(codigoProducto);
    }

    @PostMapping
    public ResponseEntity<Producto> crearProducto(@Valid @RequestBody Producto producto){
        producServ.saveProducto(producto);
        return new ResponseEntity<>(producto, HttpStatus.CREATED);
    }

    @DeleteMapping("/{codigoProducto}")
    public ResponseEntity<?> eliminarProducto(@PathVariable Long codigoProducto){
        ExceptionUtils.validateId(codigoProducto);
        producServ.deleteProducto(codigoProducto);
        return new ResponseEntity<>("Producot eliminado correctamente", HttpStatus.ACCEPTED);
    }

    @PutMapping("/{codigoProducto}")
    public ResponseEntity<Producto> editarProducto(@PathVariable Long codigoProducto,@RequestBody Producto producto){
           ExceptionUtils.validateId(codigoProducto);
           producServ.editProducto(codigoProducto,producto);
           Producto productoEditado = (producServ.findProducto(codigoProducto));
           return ResponseEntity.ok(productoEditado);

    }
}
