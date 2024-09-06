package com.proyecto.ventas.proyectoventasbazar.service;

import com.proyecto.ventas.proyectoventasbazar.model.DetalleVenta;
import com.proyecto.ventas.proyectoventasbazar.model.Producto;
import com.proyecto.ventas.proyectoventasbazar.repository.IDetalleVentaRepository;
import com.proyecto.ventas.proyectoventasbazar.repository.IProductoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetalleVentaService implements IDetalleVentaService{

    @Autowired
    IDetalleVentaRepository detalleRepo;

    @Autowired
    IProductoRepository producRepo;


    @Override
    public List<DetalleVenta> getDetalles() {
        return detalleRepo.findAll();
    }

    @Override
    public DetalleVenta findDetalle(Long id) {
        return detalleRepo.findById(id).orElse(null);
    }


    @Override

    //EN ESTA FUNCION QUISE ACTUALIZAR EL STOCK
    public void saveDetalle(DetalleVenta detalle) {

        Producto producto = producRepo.findById(detalle.getProducto().getCodigo_producto())
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

        if (producto.getStock() < detalle.getCantidad()) {
            throw new IllegalArgumentException("Stock insuficiente para el producto");
        }



        producto.setStock(producto.getStock() - detalle.getCantidad());
        producRepo.save(producto);
        detalleRepo.save(detalle);
    }


    @Override
    public void deleteDetalle(Long id) {
        detalleRepo.deleteById(id);
    }

    @Override
    public void editDetalle(DetalleVenta detalle) {
        detalleRepo.save(detalle);
    }
}
