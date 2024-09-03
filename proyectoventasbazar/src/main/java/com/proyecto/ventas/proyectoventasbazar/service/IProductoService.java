package com.proyecto.ventas.proyectoventasbazar.service;

import com.proyecto.ventas.proyectoventasbazar.model.Producto;

import java.util.List;

public interface IProductoService {


    public List<Producto> getProductos();
    public Producto findProducto(Long id);
    public void saveProducto(Producto producto);
    public void deleteProducto(Long id);
    public void editProducto(Producto producto);
    public List<Producto> getStockMen5();

}
