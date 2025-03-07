package com.proyecto.ventas.proyectoventasbazar.service;

import com.proyecto.ventas.proyectoventasbazar.dto.DetalleDTO;
import com.proyecto.ventas.proyectoventasbazar.exceptions.ResourceNotFoundException;
import com.proyecto.ventas.proyectoventasbazar.model.DetalleVenta;
import com.proyecto.ventas.proyectoventasbazar.model.Producto;
import com.proyecto.ventas.proyectoventasbazar.repository.IDetalleVentaRepository;
import com.proyecto.ventas.proyectoventasbazar.repository.IProductoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DetalleVentaService implements IDetalleVentaService{

    @Autowired
    IDetalleVentaRepository detalleRepo;

    @Autowired
    IProductoRepository producRepo;

    @Override
    public List<DetalleDTO> getDetalles() throws ResourceNotFoundException {
        List<DetalleDTO> detallesDTO = new ArrayList<>();
        List<DetalleVenta> detalles = detalleRepo.findAll();
        if(detalles == null || detalles.isEmpty()){
            throw new ResourceNotFoundException("Detalles no encontrados","P-404");
        }
        for(DetalleVenta detalle : detalleRepo.findAll()){
            DetalleDTO detalleDTO = new DetalleDTO(detalle.getIdDetalle(),detalle.getProducto().getCodigoProducto(),detalle.getCantidad(),detalle.getPrecio());
            detallesDTO.add(detalleDTO);
        }
        
        return detallesDTO;
    }

    @Override
    public DetalleVenta findDetalle(Long idDetalle) throws ResourceNotFoundException {
        return detalleRepo.findById(idDetalle).orElseThrow(()-> new ResourceNotFoundException("Detalle con la id" + idDetalle + " no encontrado"
                                    ,"P-404"));
    }

    @Override
    public DetalleDTO getDetalleDTO(Long idDetalle) {
        DetalleVenta detalleBuscar = findDetalle(idDetalle);
        DetalleDTO detalleDTO = new DetalleDTO(detalleBuscar.getIdDetalle(),detalleBuscar.getProducto().getCodigoProducto(),detalleBuscar.getCantidad(),detalleBuscar.getPrecio());
        return detalleDTO;
    }


    @Override
    @Transactional
    public void saveDetalle(DetalleVenta detalle) {
        detalleRepo.save(detalle);
    }


    @Override
    @Transactional
    public void deleteDetalle(Long idDetalle) throws ResourceNotFoundException {
        if(!detalleRepo.existsById(idDetalle)){
            throw new ResourceNotFoundException("Detalle con la id " + idDetalle + " no encontrado", "P-404");
        }
        detalleRepo.deleteById(idDetalle);
    }

    @Override
    @Transactional
    public void editDetalle(Long idDetalle, DetalleDTO detalledto) {
        DetalleVenta detalleEditar = this.findDetalle(idDetalle);
        Producto producto = producRepo.findById(detalledto.getCodigoProducto()).orElseThrow(
                ()-> new RuntimeException("Producto no encontrado"));
        detalleEditar.setCantidad(detalledto.getCantidad());
        detalleEditar.setProducto(producto);
        detalleRepo.save(detalleEditar);
    }
}
