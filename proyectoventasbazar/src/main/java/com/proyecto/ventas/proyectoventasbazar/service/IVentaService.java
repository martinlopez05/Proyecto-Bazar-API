package com.proyecto.ventas.proyectoventasbazar.service;

import com.proyecto.ventas.proyectoventasbazar.model.Venta;

import java.util.List;

public interface IVentaService {

    public List<Venta> getVentas();
    public Venta findVenta(Long id);
    public void saveVenta(Venta venta);
    public void deleteVenta(Long id);
    public void editVenta(Venta venta);
}
