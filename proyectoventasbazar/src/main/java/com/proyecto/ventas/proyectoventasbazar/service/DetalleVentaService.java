package com.proyecto.ventas.proyectoventasbazar.service;

import com.proyecto.ventas.proyectoventasbazar.dto.DetalleDTO;
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
    public DetalleVenta findDetalle(Long idDetalle) {
        return detalleRepo.findById(idDetalle).orElseThrow(()-> new RuntimeException("Detalle no encontrado"));
    }


    @Override
    public void saveDetalle(DetalleVenta detalle) {

        detalleRepo.save(detalle);
    }


    @Override
    public void deleteDetalle(Long idDetalle) {
        detalleRepo.deleteById(idDetalle);
    }

    @Override
    public void editDetalle(Long idDetalle, DetalleDTO detalledto) {
        DetalleVenta detalleEditar = this.findDetalle(idDetalle);
        Producto producto = producRepo.findById(detalledto.getCodigoProducto()).orElseThrow(
                ()-> new RuntimeException("Producto no encontrado"));
        detalleEditar.setCantidad(detalledto.getCantidad());
        detalleEditar.setProducto(producto);
        detalleRepo.save(detalleEditar);
    }
}
