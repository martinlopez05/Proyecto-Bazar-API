package com.proyecto.ventas.proyectoventasbazar.controller;

import com.proyecto.ventas.proyectoventasbazar.model.Producto;
import com.proyecto.ventas.proyectoventasbazar.service.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/productos/{codigo_producto}")
    public Producto traerProducto(@PathVariable Long codigo_producto){
        return producServ.findProducto(codigo_producto);
    }

    @GetMapping("productos/falta_stock")
    public List<Producto> traerProdStockMen5(){
        return producServ.getStockMen5();
    }

    @PostMapping("/productos/crear")
    public String crearProducto(@RequestBody Producto producto){
        producServ.saveProducto(producto);
        return "Producto creado correctamente";
    }

    @DeleteMapping("/productos/eliminar/{codigo_producto}")
    public String eliminarProducto(@PathVariable Long codigo_producto){
        producServ.deleteProducto(codigo_producto);
        return "Producto Eliminado correctamente";
    }

    @PutMapping("/productos/editar/{codigo_producto}")
    public Producto editarProducto(@PathVariable Long codigo_producto,@RequestBody Producto producto){
            Producto productoEditar = producServ.findProducto(codigo_producto);

            productoEditar.setNombre(producto.getNombre());
            productoEditar.setCosto(producto.getCosto());
            productoEditar.setMarca(producto.getMarca());
            productoEditar.setStock(producto.getStock());
            productoEditar.setVenta(producto.getVenta());

            producServ.editProducto(productoEditar);

            return producServ.findProducto(productoEditar.getCodigo_producto());
    }
}
