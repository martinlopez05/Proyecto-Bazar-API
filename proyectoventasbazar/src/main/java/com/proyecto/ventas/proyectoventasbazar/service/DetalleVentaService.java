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

    @Override
    public List<DetalleVenta> getDetalles() {
        return detalleRepo.findAll();
    }

    @Override
    public DetalleVenta findDetalle(Long id) {
        return detalleRepo.findById(id).orElseThrow(()-> new RuntimeException("Detalle no encontrado"));
    }


    @Override
    public void saveDetalle(DetalleVenta detalle) {

        detalleRepo.save(detalle);
    }


    @Override
    public void deleteDetalle(Long id) {
        detalleRepo.deleteById(id);
    }

    @Override
    public void editDetalle(Long id_detalle,DetalleVenta detalle) {
        DetalleVenta detalleEditar = this.findDetalle(id_detalle);
        detalle.setCantidad(detalleEditar.getCantidad());
        detalle.setProducto(detalleEditar.getProducto());
        detalle.setVenta(detalleEditar.getVenta());
        detalleRepo.save(detalleEditar);
    }
}
