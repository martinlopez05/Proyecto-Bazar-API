package com.proyecto.ventas.proyectoventasbazar.service;

import com.proyecto.ventas.proyectoventasbazar.model.DetalleVenta;
import com.proyecto.ventas.proyectoventasbazar.repository.IDetalleVentaRepository;
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
        return detalleRepo.findById(id).orElse(null);
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
    public void editDetalle(DetalleVenta detalle) {
        detalleRepo.save(detalle);
    }
}
