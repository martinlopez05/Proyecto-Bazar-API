package com.proyecto.ventas.proyectoventasbazar.service;

import com.proyecto.ventas.proyectoventasbazar.dto.VentaDTO;
import com.proyecto.ventas.proyectoventasbazar.exceptions.EmptyListException;
import com.proyecto.ventas.proyectoventasbazar.exceptions.InsufficientStockException;
import com.proyecto.ventas.proyectoventasbazar.exceptions.ResourceNotFoundException;
import com.proyecto.ventas.proyectoventasbazar.model.Producto;
import com.proyecto.ventas.proyectoventasbazar.model.Venta;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

public interface IVentaService {

    public List<VentaDTO> getVentas() throws EmptyListException;
    public Venta findVenta(Long codigoVenta) throws ResourceNotFoundException;
    public VentaDTO getVentaDTO(Long CodigoVenta);
    public VentaDTO saveVenta(VentaDTO ventaDTO) throws ResourceNotFoundException, InsufficientStockException ;
    public void deleteVenta(Long codigoVenta) throws ResourceNotFoundException;
    public List<Producto> getProductosVenta(Long codigoVenta) throws EmptyListException;
    public List<VentaDTO> getVentasPorCliente(Long idCliente);
    public List<VentaDTO> getVentasPorFecha(LocalDate fecha) throws EmptyListException;
}
