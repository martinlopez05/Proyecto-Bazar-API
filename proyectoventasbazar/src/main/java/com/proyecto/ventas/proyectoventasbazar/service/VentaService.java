package com.proyecto.ventas.proyectoventasbazar.service;


import com.proyecto.ventas.proyectoventasbazar.dto.DetalleDTO;
import com.proyecto.ventas.proyectoventasbazar.dto.VentaDTO;
import com.proyecto.ventas.proyectoventasbazar.model.Cliente;
import com.proyecto.ventas.proyectoventasbazar.model.DetalleVenta;
import com.proyecto.ventas.proyectoventasbazar.model.Producto;
import com.proyecto.ventas.proyectoventasbazar.model.Venta;
import com.proyecto.ventas.proyectoventasbazar.repository.IClienteRepository;
import com.proyecto.ventas.proyectoventasbazar.repository.IDetalleVentaRepository;
import com.proyecto.ventas.proyectoventasbazar.repository.IProductoRepository;
import com.proyecto.ventas.proyectoventasbazar.repository.IVentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class VentaService implements IVentaService  {


    @Autowired
    IVentaRepository ventaRepo;

    @Autowired
    IClienteRepository clienteRepo;

    @Autowired
    IProductoRepository producRepo;



    @Override
    public List<Venta> getVentas() {
        return ventaRepo.findAll();
    }

    @Override
    public Venta findVenta(Long id) {
        return ventaRepo.findById(id).orElse(null);
    }

    @Override
    public void saveVenta(VentaDTO venta) {
        Venta venta1 = new Venta();
        Cliente cliente = clienteRepo.findById(venta.getId_cliente()).orElse(null);
        venta1.setFecha_venta(venta.getFecha());
        venta1.setCliente(cliente);

        for(DetalleDTO detalleDTO : venta.getDetalles()){

            Producto producto = producRepo.findById(detalleDTO.getCodigo_producto()).orElse(null);
            DetalleVenta detalle = new DetalleVenta(producto,detalleDTO.getCantidad());
            venta1.agregarDetalle(detalle);



        }
        ventaRepo.save(venta1);


    }

    @Override
    public void deleteVenta(Long id) {
       ventaRepo.deleteById(id);
    }

    @Override
    public void editVenta(Venta venta) {
        venta.setFecha_venta(venta.getFecha_venta());
        venta.setTotal(venta.getTotal());
        venta.setCliente(venta.getCliente());
        ventaRepo.save(venta);
    }

    @Override
    public List<Producto> getProductosVenta(Long id) {
        Venta ventaBuscar = ventaRepo.findById(id).orElse(null);
        List<Producto> productos = new ArrayList<>();
        for (DetalleVenta detalle : ventaBuscar.getDetalles() ){
             productos.add(detalle.getProducto());
        }

        return productos;
    }


    @Override
    public String getMontoyCantidad(LocalDate fecha) {
        Double monto_total=0.0;
        int cant_ventas = 0;
        for (Venta venta : ventaRepo.findAll()){
            if(venta.getFecha_venta().equals(fecha)){
                monto_total+=venta.getTotal();
                cant_ventas+=1;
            }
        }

        return "El monto total de las ventas de la fecha: " + fecha + "es : " + monto_total +
                "\n La cantidad de ventas de la fecha fue :" + cant_ventas;
    }

    @Override
    public List<Venta> getVentasPorCliente(Long id_cliente) {
        List<Venta> ventasCliente = new ArrayList<>();
        for(Venta venta : ventaRepo.findAll()){
            if(venta.getCliente().getId_cliente().equals(id_cliente)){
                ventasCliente.add(venta);
            }
        }
        return ventasCliente;
    }




    public List<Venta> getVentasPorFechas(LocalDate fecha){
        List<Venta> ventas = new ArrayList<>();
        for(Venta venta : ventaRepo.findAll()){
            if(venta.getFecha_venta().equals(fecha)){
                ventas.add(venta);
            }
        }

        return  ventas;
    }


}
