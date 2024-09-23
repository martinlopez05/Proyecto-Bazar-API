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
    public List<VentaDTO> getVentas() {
        List<VentaDTO> ventasDTO = new ArrayList<>();
        for (Venta venta : ventaRepo.findAll()) {
            // Convertir los detalles de Venta a DetalleDTO
            List<DetalleDTO> detallesDTO = new ArrayList<>();

            for (DetalleVenta detalle : venta.getDetalles()) {
                DetalleDTO detalleDTO = new DetalleDTO(detalle.getIdDetalle(),detalle.getProducto().getCodigoProducto(),detalle.getCantidad(),detalle.getPrecio());
                detallesDTO.add(detalleDTO);
            }

            VentaDTO ventaDTO = new VentaDTO(venta.getCliente().getIdCliente(),venta.getCodigoVenta(), detallesDTO, venta.getFechaVenta(),venta.getTotal());
            ventasDTO.add(ventaDTO);
        }
        return ventasDTO;
    }

    @Override
    public Venta findVenta(Long codigoVenta) {
        return ventaRepo.findById(codigoVenta).orElseThrow(()-> new RuntimeException("Venta no encontrada"));
    }

    @Override
    public VentaDTO getVentaDTO(Long codigoVenta) {
        Venta ventaBuscar = this.findVenta(codigoVenta);
        List<DetalleDTO> detallesDTO = new ArrayList<>();
        for (DetalleVenta detalle : ventaBuscar.getDetalles() ){
            DetalleDTO detalleDTO = new DetalleDTO(detalle.getIdDetalle(),detalle.getProducto().getCodigoProducto(),detalle.getCantidad(),detalle.getPrecio());
            detallesDTO.add(detalleDTO);
        }

        VentaDTO ventaDTO = new VentaDTO(ventaBuscar.getCliente().getIdCliente(),ventaBuscar.getCodigoVenta(),detallesDTO, ventaBuscar.getFechaVenta(),ventaBuscar.getTotal());
        return ventaDTO;
    }


    @Override
    public void saveVenta(VentaDTO ventadto) {
        Venta venta = new Venta();
        Cliente cliente = clienteRepo.findById(ventadto.getIdCliente()).orElseThrow(()->new RuntimeException("Cliente no encontrado"));
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


    // a resolver
    @Override
    public void editVenta(Long codigoVenta, VentaDTO ventadto) {

        Venta ventaEditar = this.findVenta(codigoVenta);

        Cliente cliente = clienteRepo.findById(ventadto.getIdCliente())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        ventaEditar.setFechaVenta(ventadto.getFecha());
        ventaEditar.setCliente(cliente);

        ventaEditar.getDetalles().clear();

        for (DetalleDTO detalleDTO : ventadto.getDetalles()) {
            Producto producto = producRepo.findById(detalleDTO.getCodigoProducto())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));


            if (detalleDTO.getCantidad() <= producto.getStock()) {
                DetalleVenta detalle = new DetalleVenta(producto, detalleDTO.getCantidad());
                detalle.setPrecio(producto.getCosto()*detalle.getCantidad());
                ventaEditar.agregarDetalle(detalle);
                producto.setStock(producto.getStock() - detalleDTO.getCantidad());
                producRepo.save(producto);
            } else {
                throw new RuntimeException("Stock insuficiente del producto: " + producto.getNombre());
            }
        }

        ventaEditar.calcularTotal();

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
    public List<VentaDTO> getVentasPorCliente(Long idCliente) {
        List<VentaDTO> ventasDTOS = new ArrayList<>();
        List<Venta> ventasCliente = ventaRepo.findByClienteIdCliente(idCliente);

        for (Venta venta : ventasCliente) {
            List<DetalleDTO> detalles = new ArrayList<>();

            for (DetalleVenta detalle : venta.getDetalles()) {
                DetalleDTO detalleDTO = new DetalleDTO(detalle.getIdDetalle(), detalle.getProducto().getCodigoProducto(),
                        detalle.getCantidad(), detalle.getPrecio());
                detalles.add(detalleDTO);
            }

            VentaDTO ventaDTO = new VentaDTO(
                    venta.getCliente().getIdCliente(),
                    venta.getCodigoVenta(),
                    detalles,
                    venta.getFechaVenta(),
                    venta.getTotal()
            );
            ventasDTOS.add(ventaDTO);
        }

        return ventasDTOS;
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
