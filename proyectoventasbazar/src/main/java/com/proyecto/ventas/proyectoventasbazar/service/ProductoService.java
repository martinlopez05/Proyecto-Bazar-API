package com.proyecto.ventas.proyectoventasbazar.service;

import com.proyecto.ventas.proyectoventasbazar.model.Producto;
import com.proyecto.ventas.proyectoventasbazar.repository.IProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductoService implements IProductoService {

    @Autowired
    IProductoRepository producRepo;

    @Override
    public List<Producto> getProductos() {
        return producRepo.findAll();
    }

    @Override
    public Producto findProducto(Long id) {
        return producRepo.findById(id).orElseThrow(()->new RuntimeException("Producto no encontrado"));
    }

    @Override
    public void saveProducto(Producto producto) {
        producRepo.save(producto);
    }

    @Override
    public void deleteProducto(Long id) {
        producRepo.deleteById(id);
    }

    @Override
    public void editProducto(Long codigo_producto,Producto producto) {
        Producto productoEditar = this.findProducto(codigo_producto);
        productoEditar.setNombre(producto.getNombre());
        productoEditar.setCosto(producto.getCosto());
        productoEditar.setMarca(producto.getMarca());
        productoEditar.setStock(producto.getStock());
        producRepo.save(productoEditar);
    }

    @Override
    public List<Producto> getStockMen5(){

        List<Producto> prodStockMen5 = new ArrayList<>();
        for(Producto prod : producRepo.findAll()){
            if(prod.getStock()<5){
                prodStockMen5.add(prod);
            }
        }

        return prodStockMen5;
    }


}
