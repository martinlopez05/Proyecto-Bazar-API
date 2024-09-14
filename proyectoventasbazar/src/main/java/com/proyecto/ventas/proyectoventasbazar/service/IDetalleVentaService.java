package com.proyecto.ventas.proyectoventasbazar.service;

import com.proyecto.ventas.proyectoventasbazar.dto.DetalleDTO;
import com.proyecto.ventas.proyectoventasbazar.model.DetalleVenta;

import java.util.List;

public interface IDetalleVentaService{

    public List<DetalleVenta> getDetalles();
    public DetalleVenta findDetalle(Long idDetalle);
    public void saveDetalle(DetalleVenta detalle);
    public void deleteDetalle(Long idDetalle);
    public void editDetalle(Long idDetalle, DetalleDTO detalledto);

}

