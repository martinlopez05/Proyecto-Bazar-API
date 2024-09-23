package com.proyecto.ventas.proyectoventasbazar.service;

import com.proyecto.ventas.proyectoventasbazar.dto.DetalleDTO;
import com.proyecto.ventas.proyectoventasbazar.model.DetalleVenta;
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
    public Producto findProducto(Long codigoProducto) {
        return producRepo.findById(codigoProducto).orElseThrow(()->new RuntimeException("Producto no encontrado"));
    }

    @Override
    public void saveProducto(Producto producto) {
        producRepo.save(producto);
    }

    @Override
    public void deleteProducto(Long codigoProducto) {
        producRepo.deleteById(codigoProducto);
    }

    @Override
    public void editProducto(Long codigoProducto,Producto producto) {
        Producto productoEditar = this.findProducto(codigoProducto);
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

    @Override
    public List<DetalleDTO> getDetallesporProducto(Long codigoProducto) {
        Producto producto = this.findProducto(codigoProducto);
        List<DetalleDTO> detalles = new ArrayList<>();
        for (DetalleVenta detalle : producto.getDetallesProduc()){
            DetalleDTO detalleDTO = new DetalleDTO(detalle.getIdDetalle(),detalle.getProducto().getCodigoProducto(),detalle.getCantidad()
            ,detalle.getPrecio());
            detalles.add(detalleDTO);

        }

        return detalles;
    }


}
