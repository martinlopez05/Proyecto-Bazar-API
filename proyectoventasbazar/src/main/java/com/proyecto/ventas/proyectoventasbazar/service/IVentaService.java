package com.proyecto.ventas.proyectoventasbazar.service;

import com.proyecto.ventas.proyectoventasbazar.dto.VentaDTO;
import com.proyecto.ventas.proyectoventasbazar.model.Producto;
import com.proyecto.ventas.proyectoventasbazar.model.Venta;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

public interface IVentaService {

    public List<Venta> getVentas();
    public Venta findVenta(Long id);
    public void saveVenta(VentaDTO ventaDTO);
    public void deleteVenta(Long id);
    public void editVenta(Long codigo_venta,Venta venta);
    public List<Producto> getProductosVenta(Long id);
    public String getMontoyCantidad(LocalDate fecha);
    public List<Venta> getVentasPorCliente(Long id_cliente);
}
