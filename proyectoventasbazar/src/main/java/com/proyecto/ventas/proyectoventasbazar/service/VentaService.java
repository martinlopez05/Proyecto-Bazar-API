package com.proyecto.ventas.proyectoventasbazar.service;


import com.proyecto.ventas.proyectoventasbazar.model.Venta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
