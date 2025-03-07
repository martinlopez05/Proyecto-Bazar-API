package com.proyecto.ventas.proyectoventasbazar.service;

import com.proyecto.ventas.proyectoventasbazar.dto.DetalleDTO;
import com.proyecto.ventas.proyectoventasbazar.exceptions.EmptyListException;
import com.proyecto.ventas.proyectoventasbazar.exceptions.ExceptionUtils;
import com.proyecto.ventas.proyectoventasbazar.exceptions.ResourceNotFoundException;
import com.proyecto.ventas.proyectoventasbazar.model.DetalleVenta;
import com.proyecto.ventas.proyectoventasbazar.model.Producto;
import com.proyecto.ventas.proyectoventasbazar.repository.IProductoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Servicio que implementa la lógica de negocio para la gestión de productos.
 * Proporciona métodos para realizar operaciones CRUD sobre la entidad Producto.
 */
@Service
public class ProductoService implements IProductoService {

    @Autowired
    IProductoRepository producRepo;

    /**
     * Obtiene la lista de todos los productos disponibles en el sistema.
     *
     * @return Lista de objetos Producto.
     * @throws ResourceNotFoundException Si no se encuentran productos en el
     * sistema.
     */
    @Override
    public List<Producto> getProductos() throws EmptyListException {
        List<Producto> productos = producRepo.findAll();
        ExceptionUtils.validateListNotEmpty(productos, "No se han cargado productos en el sistema");
        return productos;
    }

    /**
     * Busca un producto por su código.
     *
     * @param codigoProducto El código único del producto.
     * @return El objeto Producto correspondiente al código proporcionado.
     * @throws ResourceNotFoundException Si el producto con el código no es
     * encontrado.
     */
    @Override
    public Producto findProducto(Long codigoProducto) throws ResourceNotFoundException {
        return producRepo.findById(codigoProducto).orElseThrow(() -> new ResourceNotFoundException("Producto con codigo" + codigoProducto + " no encontrado",
                "P-404"));
    }

    /**
     * Guarda un nuevo producto en el sistema.
     *
     * @param producto El objeto Producto a guardar.
     */
    @Override
    @Transactional
    public void saveProducto(Producto producto) {
        producRepo.save(producto);
    }

    /**
     * Elimina un producto del sistema utilizando su código.
     *
     * @param codigoProducto El código del producto a eliminar.
     * @throws ResourceNotFoundException Si el producto no existe en el sistema.
     */
    @Override
    @Transactional
    public void deleteProducto(Long codigoProducto) throws ResourceNotFoundException {
        if (!producRepo.existsById(codigoProducto)) {
            throw new ResourceNotFoundException("Producto con codigo " + codigoProducto + " no encontrado", "P-404");
        }
        producRepo.deleteById(codigoProducto);
    }

    /**
     * Edita los detalles de un producto en el sistema.
     *
     * @param codigoProducto El código del producto a editar.
     * @param producto El objeto Producto con los nuevos detalles.
     */
    @Override
    @Transactional
    public void editProducto(Long codigoProducto, Producto producto) {
        Producto productoEditar = this.findProducto(codigoProducto);

        if (producto.getNombre() != null) {
            productoEditar.setNombre(producto.getNombre());
        }
        if (producto.getCosto() != null) {
            productoEditar.setCosto(producto.getCosto());
        }
        if (producto.getMarca() != null) {
            productoEditar.setMarca(producto.getMarca());
        }
        if (producto.getStock() != null) {
            productoEditar.setStock(producto.getStock());
        }

        producRepo.save(productoEditar);
    }

    /**
     * Obtiene los productos cuyo stock es menor al valor proporcionado.
     *
     * @param stock El valor límite de stock.
     * @return Lista de productos con stock menor al valor proporcionado.
     * @throws EmptyListException Si no se encuentran productos con stock
     * inferior al valor especificado.
     */
    @Override
    public List<Producto> getStockMenorA(int stock) throws EmptyListException {

        List<Producto> prodStockMenor = new ArrayList<>();
        for (Producto prod : producRepo.findAll()) {
            if (prod.getStock() < stock) {
                prodStockMenor.add(prod);
            }
        }
        ExceptionUtils.validateListNotEmpty(prodStockMenor, "Productos con stock menor a " + stock + " no encontrados");
        return prodStockMenor;
    }

    /**
     * Obtiene los detalles de ventas asociadas a un producto específico.
     *
     * @param codigoProducto El código del producto a buscar.
     * @return Lista de objetos DetalleDTO correspondientes al producto.
     * @throws ResourceNotFoundException Si el producto con el código no es
     * encontrado.
     */
    @Override
    public List<DetalleDTO> getDetallesporProducto(Long codigoProducto) throws EmptyListException {
        Producto producto = this.findProducto(codigoProducto);
        List<DetalleDTO> detalles = new ArrayList<>();
        for (DetalleVenta detalle : producto.getDetallesProduc()) {
            DetalleDTO detalleDTO = new DetalleDTO(detalle.getIdDetalle(), detalle.getProducto().getCodigoProducto(), detalle.getCantidad(),
                    detalle.getPrecio());
            detalles.add(detalleDTO);

        }
        ExceptionUtils.validateListNotEmpty(detalles, "no se han cargado detalles del producto con codigo" + codigoProducto);
        return detalles;
    }

}
