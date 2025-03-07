package com.proyecto.ventas.proyectoventasbazar.service;


import com.proyecto.ventas.proyectoventasbazar.dto.DetalleDTO;
import com.proyecto.ventas.proyectoventasbazar.dto.VentaDTO;
import com.proyecto.ventas.proyectoventasbazar.exceptions.EmptyListException;
import com.proyecto.ventas.proyectoventasbazar.exceptions.ExceptionUtils;
import com.proyecto.ventas.proyectoventasbazar.exceptions.InsufficientStockException;
import com.proyecto.ventas.proyectoventasbazar.exceptions.ResourceNotFoundException;
import com.proyecto.ventas.proyectoventasbazar.model.Cliente;
import com.proyecto.ventas.proyectoventasbazar.model.DetalleVenta;
import com.proyecto.ventas.proyectoventasbazar.model.Producto;
import com.proyecto.ventas.proyectoventasbazar.model.Venta;
import com.proyecto.ventas.proyectoventasbazar.repository.IClienteRepository;
import com.proyecto.ventas.proyectoventasbazar.repository.IDetalleVentaRepository;
import com.proyecto.ventas.proyectoventasbazar.repository.IProductoRepository;
import com.proyecto.ventas.proyectoventasbazar.repository.IVentaRepository;
import jakarta.transaction.Transactional;
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

            VentaDTO ventaDTO = new VentaDTO(venta.getCliente().getIdCliente(),venta.getCodigoVenta(), detallesDTO,venta.getTotal());
            ventaDTO.setFecha(venta.getFechaVenta());
            ventasDTO.add(ventaDTO);
        }
        return ventasDTO;
    }

    @Override
    public Venta findVenta(Long codigoVenta) {
        return ventaRepo.findById(codigoVenta).orElseThrow(()-> new ResourceNotFoundException
                        ("Venta con el codigo " + codigoVenta + " no encontrada" , "P-404"));
    }

    @Override
    public VentaDTO getVentaDTO(Long codigoVenta) {
        Venta ventaBuscar = this.findVenta(codigoVenta);
        List<DetalleDTO> detallesDTO = new ArrayList<>();
        for (DetalleVenta detalle : ventaBuscar.getDetalles() ){
            DetalleDTO detalleDTO = new DetalleDTO(detalle.getIdDetalle(),detalle.getProducto().getCodigoProducto(),detalle.getCantidad(),detalle.getPrecio());
            detallesDTO.add(detalleDTO);
        }

        VentaDTO ventaDTO = new VentaDTO(ventaBuscar.getCliente().getIdCliente(),ventaBuscar.getCodigoVenta(),detallesDTO,ventaBuscar.getTotal());
        ventaDTO.setFecha(ventaBuscar.getFechaVenta());
        return ventaDTO;
    }


    @Override
    @Transactional
    public void saveVenta(VentaDTO ventadto) throws InsufficientStockException {
        Venta venta = new Venta();
        Cliente cliente = clienteRepo.findById(ventadto.getIdCliente()).
                orElseThrow(() -> new ResourceNotFoundException("Cliente con la id " + ventadto.getIdCliente()
                            +"no encontrado" , "P-404"));
        
        venta.setCliente(cliente);
        for (DetalleDTO detalleDTO : ventadto.getDetalles()) {
            Producto producto = producRepo.findById(detalleDTO.getCodigoProducto())
                    .orElseThrow(() -> new ResourceNotFoundException("Producto con id " + detalleDTO.getCodigoProducto() + "no encontrado", "P-404"));

            if (detalleDTO.getCantidad() <= producto.getStock()) {
                DetalleVenta detalle = new DetalleVenta(producto, detalleDTO.getCantidad());
                venta.agregarDetalle(detalle);
                producto.setStock(producto.getStock() - detalleDTO.getCantidad());
                producRepo.save(producto);
            } else {
                throw new InsufficientStockException("Stock insuficiente del producto: " + producto.getNombre()
                                + "Stock disponible: " + producto.getStock()   + ". Cantidad solicitada: " + detalleDTO.getCantidad(), "P-400");
            }
        }

        venta.calcularTotal();
        ventaRepo.save(venta);
    }


    @Override
    @Transactional
    public void deleteVenta(Long codigoVenta) {
       if(!ventaRepo.existsById(codigoVenta)){
            throw new ResourceNotFoundException("Venta con codigo " + codigoVenta + " no encontrada", "P-404" );
       }
       ventaRepo.deleteById(codigoVenta);
    }


    @Override
    @Transactional
    public void editVenta(Long codigoVenta, VentaDTO ventadto) {
        Venta ventaEditar = this.findVenta(codigoVenta);
        Cliente cliente = clienteRepo.findById(ventadto.getIdCliente())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        ventaEditar.setCliente(cliente);

        LocalDate fechaOriginal = ventaEditar.getFechaVenta();

        ventaEditar.getDetalles().clear();

        for (DetalleDTO detalleDTO : ventadto.getDetalles()) {
            Producto producto = producRepo.findById(detalleDTO.getCodigoProducto())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            if (detalleDTO.getCantidad() <= producto.getStock()) {
                DetalleVenta detalle = new DetalleVenta(producto, detalleDTO.getCantidad());
                ventaEditar.agregarDetalle(detalle);
                producto.setStock(producto.getStock() - detalleDTO.getCantidad());
                producRepo.save(producto);
            } else {
                throw new RuntimeException("Stock insuficiente del producto: " + producto.getNombre());
            }
        }

        ventaEditar.setFechaVenta(fechaOriginal);
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
        
        ExceptionUtils.validateListNotEmpty(ventasCliente, "No se ha cargado ninguna venta al cliente con id "
                                            + idCliente + " en el sistema");

        for (Venta venta : ventasCliente) {
            List<DetalleDTO> detalles = new ArrayList<>();

            for (DetalleVenta detalle : venta.getDetalles()) {
                DetalleDTO detalleDTO = new DetalleDTO(detalle.getIdDetalle(), detalle.getProducto().getCodigoProducto(),
                        detalle.getCantidad(), detalle.getPrecio());
                detalles.add(detalleDTO);
            }

            VentaDTO ventaDTO = new VentaDTO(venta.getCliente().getIdCliente(), venta.getCodigoVenta(), detalles, venta.getTotal());
            ventaDTO.setFecha(venta.getFechaVenta());
            ventasDTOS.add(ventaDTO);
        }

        return ventasDTOS;
    }


    public List<Venta> getVentasPorFechas(LocalDate fecha) throws EmptyListException{
        List<Venta> ventas = new ArrayList<>();
        for(Venta venta : ventaRepo.findAll()){
            if(venta.getFechaVenta().equals(fecha)){
                ventas.add(venta);
            }
        }
        
        ExceptionUtils.validateListNotEmpty(ventas, "No se ha cargado ninguna venta en la fecha " + fecha + " en el sistema");

        return  ventas;
    }


}
