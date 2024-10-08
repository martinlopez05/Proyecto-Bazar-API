package com.proyecto.ventas.proyectoventasbazar.service;

import com.proyecto.ventas.proyectoventasbazar.dto.VentaDTO;
import com.proyecto.ventas.proyectoventasbazar.model.Producto;
import com.proyecto.ventas.proyectoventasbazar.model.Venta;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

public interface IVentaService {

    public List<VentaDTO> getVentas();
    public Venta findVenta(Long codigoVenta);
    public VentaDTO getVentaDTO(Long CodigoVenta);
    public void saveVenta(VentaDTO ventaDTO);
    public void deleteVenta(Long codigoVenta);
    public void editVenta(Long codigoVenta,VentaDTO ventadto);
    public List<Producto> getProductosVenta(Long codigoVenta);
    public List<VentaDTO> getVentasPorCliente(Long idCliente);
}
