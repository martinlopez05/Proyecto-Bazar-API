package com.proyecto.ventas.proyectoventasbazar.controller;

import com.proyecto.ventas.proyectoventasbazar.model.Producto;
import com.proyecto.ventas.proyectoventasbazar.service.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductoController {
    @Autowired
    IProductoService producServ;

    @GetMapping("/productos")
    public List<Producto> traerProductos (){
        return producServ.getProductos();
    }

    @GetMapping("/productos/{codigoProducto}")
    public Producto traerProducto(@PathVariable Long codigoProducto){
        return producServ.findProducto(codigoProducto);
    }

    @GetMapping("productos/falta_stock")
    public List<Producto> traerProdStockMen5(){
        return producServ.getStockMen5();
    }

    @PostMapping("productos/crear")
    public String crearProducto(@RequestBody Producto producto){
        producServ.saveProducto(producto);
        return "Producto creado correctamente";
    }

    @DeleteMapping("/productos/eliminar/{codigoProducto}")
    public String eliminarProducto(@PathVariable Long codigoProducto){
        producServ.deleteProducto(codigoProducto);
        return "Producto Eliminado correctamente";
    }

    @PutMapping("/productos/editar/{codigoProducto}")
    public Producto editarProducto(@PathVariable Long codigoProducto,@RequestBody Producto producto){
           producServ.editProducto(codigoProducto,producto);
           return producServ.findProducto(codigoProducto);

    }
}
