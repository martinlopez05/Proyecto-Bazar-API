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

/**
 * Servicio que implementa la lógica de negocio para la gestión de ventas.
 * Proporciona métodos para realizar operaciones CRUD sobre la entidad Venta.
 */
@Service
public class VentaService implements IVentaService {

    @Autowired
    IVentaRepository ventaRepo;

    @Autowired
    IClienteRepository clienteRepo;

    @Autowired
    IProductoRepository producRepo;

    /**
     * Obtiene todas las ventas en el sistema y las devuelve en formato DTO.
     *
     * @return Lista de objetos {@link VentaDTO} que representan todas las
     * ventas.
     */
    @Override
    public List<VentaDTO> getVentas() throws EmptyListException {
        List<VentaDTO> ventasDTO = new ArrayList<>();
        List<Venta> ventas = ventaRepo.findAll();
        ExceptionUtils.validateListNotEmpty(ventas, "No se han cargado ventas en el sistema");
        for (Venta venta : ventas) {
            // Convertir los detalles de Venta a DetalleDTO
            List<DetalleDTO> detallesDTO = new ArrayList<>();

            for (DetalleVenta detalle : venta.getDetalles()) {
                DetalleDTO detalleDTO = new DetalleDTO(detalle.getIdDetalle(), detalle.getProducto().getCodigoProducto(), detalle.getCantidad(), detalle.getPrecio());
                detallesDTO.add(detalleDTO);
            }

            VentaDTO ventaDTO = new VentaDTO(venta.getCliente().getIdCliente(), venta.getCodigoVenta(), detallesDTO, venta.getTotal());
            ventaDTO.setFecha(venta.getFechaVenta());
            ventasDTO.add(ventaDTO);
        }
        return ventasDTO;
    }

    /**
     * Busca una venta por su código. Lanza una excepción si no se encuentra la
     * venta.
     *
     * @param codigoVenta El código de la venta que se desea buscar.
     * @return La venta correspondiente al código proporcionado.
     * @throws ResourceNotFoundException Si no se encuentra la venta.
     */
    @Override
    public Venta findVenta(Long codigoVenta) throws ResourceNotFoundException {
        return ventaRepo.findById(codigoVenta).orElseThrow(() -> new ResourceNotFoundException("Venta con el codigo " + codigoVenta + " no encontrada", "P-404"));
    }

    /**
     * Obtiene una venta en formato DTO dado su código.
     *
     * @param codigoVenta El código de la venta que se desea obtener.
     * @return Un objeto {@link VentaDTO} que representa la venta.
     */
    @Override
    public VentaDTO getVentaDTO(Long codigoVenta) {
        Venta ventaBuscar = this.findVenta(codigoVenta);
        List<DetalleDTO> detallesDTO = new ArrayList<>();
        for (DetalleVenta detalle : ventaBuscar.getDetalles()) {
            DetalleDTO detalleDTO = new DetalleDTO(detalle.getIdDetalle(), detalle.getProducto().getCodigoProducto(), detalle.getCantidad(), detalle.getPrecio());
            detallesDTO.add(detalleDTO);
        }

        VentaDTO ventaDTO = new VentaDTO(ventaBuscar.getCliente().getIdCliente(), ventaBuscar.getCodigoVenta(), detallesDTO, ventaBuscar.getTotal());
        ventaDTO.setFecha(ventaBuscar.getFechaVenta());
        return ventaDTO;
    }

    /**
     * Guarda una nueva venta en el sistema. Lanza excepciones si el cliente o
     * los productos no se encuentran, o si hay problemas con el stock.
     *
     * @param ventadto El objeto {@link VentaDTO} que contiene los detalles de
     * la venta.
     * @return Un objeto {@link VentaDTO} que representa la venta.
     * 
     * @throws ResourceNotFoundException Si no se encuentra el cliente o el
     * producto.
     * @throws InsufficientStockException Si el stock del producto es
     * insuficiente.
     * 
     * 
     */
    @Override
    @Transactional
    public VentaDTO saveVenta(VentaDTO ventadto) throws ResourceNotFoundException, InsufficientStockException {
        Venta venta = new Venta();
        Cliente cliente = clienteRepo.findById(ventadto.getIdCliente()).
                orElseThrow(() -> new ResourceNotFoundException("Cliente con la id " + ventadto.getIdCliente()
                + " no encontrado", "P-404"));

        venta.setCliente(cliente);
        for (DetalleDTO detalleDTO : ventadto.getDetalles()) {
            Producto producto = producRepo.findById(detalleDTO.getCodigoProducto())
                    .orElseThrow(() -> new ResourceNotFoundException("Producto con codigo " + detalleDTO.getCodigoProducto() + " no encontrado", "P-404"));

            if (detalleDTO.getCantidad() <= producto.getStock()) {
                DetalleVenta detalle = new DetalleVenta(producto, detalleDTO.getCantidad());
                venta.agregarDetalle(detalle);
                producto.setStock(producto.getStock() - detalleDTO.getCantidad());
                producRepo.save(producto);
            } else {
                throw new InsufficientStockException("Stock insuficiente del producto: " + producto.getNombre()
                        + "Stock disponible: " + producto.getStock() + ". Cantidad solicitada: " + detalleDTO.getCantidad(), "P-400");
            }
        }

        venta.calcularTotal();
        ventaRepo.save(venta);
        return this.getVentaDTO(venta.getCodigoVenta());
        
    }

    /**
     * Elimina una venta dada su código. Lanza una excepción si no se encuentra
     * la venta.
     *
     * @param codigoVenta El código de la venta a eliminar.
     * @throws ResourceNotFoundException Si no se encuentra la venta.
     */
    @Override
    @Transactional
    public void deleteVenta(Long codigoVenta) throws ResourceNotFoundException {
        if (!ventaRepo.existsById(codigoVenta)) {
            throw new ResourceNotFoundException("Venta con codigo " + codigoVenta + " no encontrada", "P-404");
        }
        ventaRepo.deleteById(codigoVenta);
    }


    /**
     * Obtiene la lista de productos asociados a una venta dada por su código.
     *
     * @param codigoVenta El código de la venta.
     * @return Lista de productos asociados a la venta.
     * @throws EmptyListException Si no se encuentran ventas en la fecha
     * indicada.
     */
    @Override
    public List<Producto> getProductosVenta(Long codigoVenta) throws EmptyListException{

        Venta ventaBuscar = this.findVenta(codigoVenta);
        List<Producto> productos = new ArrayList<>();
        for (DetalleVenta detalle : ventaBuscar.getDetalles()) {
            productos.add(detalle.getProducto());
        }
        ExceptionUtils.validateListNotEmpty(productos, "No se encuentra ningun producto cargado a la venta con codigo"
                + codigoVenta);

        return productos;

    }

    /**
     * Obtiene todas las ventas realizadas a un cliente específico, dado su ID.
     *
     * @param idCliente El ID del cliente.
     * @return Lista de objetos {@link VentaDTO} representando las ventas
     * realizadas al cliente.
     */
    @Override
    public List<VentaDTO> getVentasPorCliente(Long idCliente) {
        List<VentaDTO> ventasDTOS = new ArrayList<>();
        List<Venta> ventasCliente = ventaRepo.findByClienteIdCliente(idCliente);

        ExceptionUtils.validateListNotEmpty(ventasCliente, "No se ha cargado ninguna venta al cliente con id "
                + idCliente + " en el sistema");

        for (Venta venta : ventasCliente) {
            ventasDTOS.add(this.getVentaDTO(venta.getCodigoVenta()));
        }

        return ventasDTOS;
    }

    /**
     * Obtiene las ventas realizadas en una fecha específica.
     *
     * @param fecha La fecha de las ventas a buscar.
     * @return Lista de objetos {@link VentaDTO} que representan las ventas
     * realizadas en la fecha proporcionada.
     * @throws EmptyListException Si no se encuentran ventas en la fecha
     * indicada.
     */
    @Override
    public List<VentaDTO> getVentasPorFecha(LocalDate fecha) throws EmptyListException {
        List<Venta> ventas = new ArrayList<>();
        List<VentaDTO> ventasDTO = new ArrayList<>();
        for (Venta venta : ventaRepo.findAll()) {
            if (venta.getFechaVenta().equals(fecha)) {
                ventas.add(venta);
            }
        }
        for(Venta venta : ventas ){
            ventasDTO.add(this.getVentaDTO(venta.getCodigoVenta()));
        }

        ExceptionUtils.validateListNotEmpty(ventas, "No se ha cargado ninguna venta en la fecha " + fecha + " en el sistema");

        return ventasDTO;
    }

}
