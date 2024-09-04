package com.proyecto.ventas.proyectoventasbazar.service;

import com.proyecto.ventas.proyectoventasbazar.model.DetalleVenta;

import java.util.List;

public interface IDetalleVentaService{

    public List<DetalleVenta> getDetalles();
    public DetalleVenta findDetalle(Long id);
    public void saveDetalle(DetalleVenta detalle);
    public void deleteDetalle(Long id);
    public void editDetalle(DetalleVenta detalle);

}

