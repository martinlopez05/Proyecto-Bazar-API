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

@Service
public class ProductoService implements IProductoService {

    @Autowired
    IProductoRepository producRepo;

    @Override
    public List<Producto> getProductos() throws ResourceNotFoundException  {
        List<Producto> productos = producRepo.findAll();
        ExceptionUtils.validateListNotEmpty(productos, "No se han cargado productos en el sistema");
        return productos;
    }

    @Override
    public Producto findProducto(Long codigoProducto) throws ResourceNotFoundException {
        return producRepo.findById(codigoProducto).orElseThrow(()->new ResourceNotFoundException("Producto con codigo" + codigoProducto + " no encontrado"
                                   ,"P-404"));
    }

    @Override
    @Transactional
    public void saveProducto(Producto producto) {
        producRepo.save(producto);
    }

    @Override
    @Transactional
    public void deleteProducto(Long codigoProducto) throws ResourceNotFoundException {
        if(!producRepo.existsById(codigoProducto)){
            throw new ResourceNotFoundException("Producto con codigo " + codigoProducto + " no encontrado", "P-404" );
        }
        producRepo.deleteById(codigoProducto);
    }

    @Override
    @Transactional
    public void editProducto(Long codigoProducto,Producto producto) {
        Producto productoEditar = this.findProducto(codigoProducto);
        productoEditar.setNombre(producto.getNombre());
        productoEditar.setCosto(producto.getCosto());
        productoEditar.setMarca(producto.getMarca());
        productoEditar.setStock(producto.getStock());
        producRepo.save(productoEditar);
    }


    @Override
    public List<Producto> getStockMenorA(int stock) throws EmptyListException{

        List<Producto> prodStockMenor = new ArrayList<>();
        for(Producto prod : producRepo.findAll()){
            if(prod.getStock()< stock){
                prodStockMenor.add(prod);
            }
        }
        ExceptionUtils.validateListNotEmpty(prodStockMenor, "Productos con stock menor a" + stock +  "no encontrados");
        return prodStockMenor;
    }

    @Override
    public List<DetalleDTO> getDetallesporProducto(Long codigoProducto) throws ResourceNotFoundException {
        Producto producto = this.findProducto(codigoProducto);
        List<DetalleDTO> detalles = new ArrayList<>();
        for (DetalleVenta detalle : producto.getDetallesProduc()){
            DetalleDTO detalleDTO = new DetalleDTO(detalle.getIdDetalle(),detalle.getProducto().getCodigoProducto(),detalle.getCantidad()
            ,detalle.getPrecio());
            detalles.add(detalleDTO);

        }
        ExceptionUtils.validateListNotEmpty(detalles, "no se han cargado detalles del producto con codigo" + codigoProducto);
        return detalles;
    }
    
    
    


}
