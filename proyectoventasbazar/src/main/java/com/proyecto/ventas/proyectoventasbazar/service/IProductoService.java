package com.proyecto.ventas.proyectoventasbazar.service;

import com.proyecto.ventas.proyectoventasbazar.dto.DetalleDTO;
import com.proyecto.ventas.proyectoventasbazar.exceptions.EmptyListException;
import com.proyecto.ventas.proyectoventasbazar.exceptions.ResourceNotFoundException;
import com.proyecto.ventas.proyectoventasbazar.model.Producto;

import java.util.List;

public interface IProductoService {


    public List<Producto> getProductos() throws EmptyListException;
    public Producto findProducto(Long codigoProducto) throws ResourceNotFoundException;
    public void saveProducto(Producto producto);
    public void deleteProducto(Long codigoProducto) throws ResourceNotFoundException;
    public void editProducto(Long codigoProducto,Producto producto);
    public List<Producto> getStockMenorA(int stock) throws EmptyListException;
    public List<DetalleDTO> getDetallesporProducto(Long codidgoProducto) throws EmptyListException;

}
