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
        return producRepo.findById(id).orElse(null);
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
    public void editProducto(Producto producto) {
       producRepo.save(producto);
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
