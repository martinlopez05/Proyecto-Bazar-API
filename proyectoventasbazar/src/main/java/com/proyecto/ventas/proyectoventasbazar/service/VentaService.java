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
    public Venta findVenta(Long codigoVenta) {
        return ventaRepo.findById(codigoVenta).orElseThrow(()->new RuntimeException("Venta no encontrada"));
    }

    @Override
    public void saveVenta(VentaDTO ventadto) {
        Venta venta = new Venta();
        Cliente cliente = clienteRepo.findById(ventadto.getIdCliente()).orElseThrow(()->new RuntimeException("Cliente no encontrado"));
        venta.setFechaVenta(ventadto.getFecha());
        venta.setCliente(cliente);

        for(DetalleDTO detalleDTO : ventadto.getDetalles()){

            Producto producto = producRepo.findById(detalleDTO.getCodigoProducto()).orElseThrow(
                    ()->new RuntimeException("Producto no encontrado"));
            if(detalleDTO.getCantidad()<=producto.getStock()){
                DetalleVenta detalle = new DetalleVenta(producto,detalleDTO.getCantidad());
                venta.agregarDetalle(detalle);
                producto.setStock(producto.getStock() - detalleDTO.getCantidad());
                producRepo.save(producto);

            }
            else{
                throw new RuntimeException("Stock insuficiente del producto: " + producto.getNombre());
            }



        }
        ventaRepo.save(venta);
    }

    @Override
    public void deleteVenta(Long codigoVenta) {
       ventaRepo.deleteById(codigoVenta);
    }

    @Override
    public void editVenta(Long codigoVenta,VentaDTO ventadto) {
        Venta ventaEditar = this.findVenta(codigoVenta);
        Cliente cliente = clienteRepo.findById(ventadto.getIdCliente()).orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        ventaEditar.setFechaVenta(ventadto.getFecha());
        ventaEditar.setCliente(cliente);

        for (DetalleDTO detalleDTO : ventadto.getDetalles()) {

            Producto producto = producRepo.findById(detalleDTO.getCodigoProducto()).orElseThrow(
                    () -> new RuntimeException("Producto no encontrado"));
            if (detalleDTO.getCantidad() <= producto.getStock()) {
                DetalleVenta detalle = new DetalleVenta(producto, detalleDTO.getCantidad());
                ventaEditar.agregarDetalle(detalle);
                producto.setStock(producto.getStock() - detalleDTO.getCantidad());
                producRepo.save(producto);

            } else {
                throw new RuntimeException("Stock insuficiente del producto: " + producto.getNombre());
            }


        }
        ventaRepo.save(ventaEditar);
    }




    @Override
    public List<Producto> getProductosVenta(Long codigoVenta) {
        Venta ventaBuscar = this.findVenta(codigoVenta);
        List<Producto> productos = new ArrayList<>();
        for (DetalleVenta detalle : ventaBuscar.getDetalles() ){
             productos.add(detalle.getProducto());
        }

        return productos;
    }




    @Override
    public List<Venta> getVentasPorCliente(Long idCliente) {
        List<Venta> ventasCliente = new ArrayList<>();
        for(Venta venta : ventaRepo.findAll()){
            if(venta.getCliente().getIdCliente().equals(idCliente)){
                ventasCliente.add(venta);
            }
        }
        return ventasCliente;
    }




    public List<Venta> getVentasPorFechas(LocalDate fecha){
        List<Venta> ventas = new ArrayList<>();
        for(Venta venta : ventaRepo.findAll()){
            if(venta.getFechaVenta().equals(fecha)){
                ventas.add(venta);
            }
        }

        return  ventas;
    }


}
