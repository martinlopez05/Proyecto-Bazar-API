package com.proyecto.ventas.proyectoventasbazar.service;

import com.proyecto.ventas.proyectoventasbazar.model.Producto;

import java.util.List;

public interface IProductoService {


    public List<Producto> getProductos();
    public Producto findProducto(Long codigoProducto);
    public void saveProducto(Producto producto);
    public void deleteProducto(Long codigoProducto);
    public void editProducto(Long codigoProducto,Producto producto);
    public List<Producto> getStockMen5();

}
