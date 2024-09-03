package com.proyecto.ventas.proyectoventasbazar.service;

import com.proyecto.ventas.proyectoventasbazar.model.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService implements IProductoService {

    @Autowired
    IProductoService producRepo;

    @Override
    public List<Producto> getProductos() {
        return producRepo.getProductos();
    }

    @Override
    public Producto findProducto(Long id) {
        return producRepo.findProducto(id);
    }

    @Override
    public void saveProducto(Producto producto) {
        producRepo.saveProducto(producto);
    }

    @Override
    public void deleteProducto(Long id) {
        producRepo.deleteProducto(id);
    }

    @Override
    public void editProducto(Producto producto) {
       producRepo.saveProducto(producto);
    }
}
