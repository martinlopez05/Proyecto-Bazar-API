package com.proyecto.ventas.proyectoventasbazar.service;


import com.proyecto.ventas.proyectoventasbazar.model.Producto;
import com.proyecto.ventas.proyectoventasbazar.model.Venta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class VentaService implements IVentaService  {


    @Autowired
    IVentaService ventaRepo;

    @Override
    public List<Venta> getVentas() {
        return ventaRepo.getVentas();
    }

    @Override
    public Venta findVenta(Long id) {
        return ventaRepo.findVenta(id);
    }

    @Override
    public void saveVenta(Venta venta) {
       ventaRepo.saveVenta(venta);
    }

    @Override
    public void deleteVenta(Long id) {
       ventaRepo.deleteVenta(id);
    }

    @Override
    public void editVenta(Venta venta) {
       ventaRepo.saveVenta(venta);
    }

    @Override
    public List<Producto> getProductosVenta(Long id) {
        Venta venta = ventaRepo.findVenta(id);
        return venta.getProductos();


    }

    @Override
    public String getMontoyCantidad(LocalDate fecha) {
        Double monto_total=0.0;
        int cant_ventas = 0;
        for (Venta venta : ventaRepo.getVentas()){
            if(venta.getFecha_venta().equals(fecha)){
                monto_total+=venta.getTotal();
                cant_ventas+=1;
            }
        }

        return "El monto total de las ventas de la fecha: " + fecha + "es : " + monto_total +
                "\n La cantidad de ventas de la fecha fue :" + cant_ventas;
    }
}
