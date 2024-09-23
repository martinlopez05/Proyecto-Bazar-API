package com.proyecto.ventas.proyectoventasbazar.service;

import com.proyecto.ventas.proyectoventasbazar.dto.DetalleDTO;
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
    public List<DetalleDTO> getDetalles() {
        List<DetalleDTO> detalles = new ArrayList<>();
        for(DetalleVenta detalle : detalleRepo.findAll()){
            DetalleDTO detalleDTO = new DetalleDTO(detalle.getIdDetalle(),detalle.getProducto().getCodigoProducto(),detalle.getCantidad(),detalle.getPrecio());
            detalles.add(detalleDTO);
        }
        return detalles;
    }

    @Override
    public DetalleVenta findDetalle(Long idDetalle) {
        return detalleRepo.findById(idDetalle).orElseThrow(()-> new RuntimeException("Detalle no encontrado"));
    }

    @Override
    public DetalleDTO getDetalleDTO(Long idDetalle) {
        DetalleVenta detalleBuscar = findDetalle(idDetalle);
        DetalleDTO detalleDTO = new DetalleDTO(detalleBuscar.getIdDetalle(),detalleBuscar.getProducto().getCodigoProducto(),detalleBuscar.getCantidad(),detalleBuscar.getPrecio());
        return detalleDTO;
    }


    @Override
    public void saveDetalle(DetalleVenta detalle) {

        detalleRepo.save(detalle);
    }


    @Override
    public void deleteDetalle(Long idDetalle) {
        detalleRepo.deleteById(idDetalle);
    }

    @Override
    public void editDetalle(Long idDetalle, DetalleDTO detalledto) {
        DetalleVenta detalleEditar = this.findDetalle(idDetalle);
        Producto producto = producRepo.findById(detalledto.getCodigoProducto()).orElseThrow(
                ()-> new RuntimeException("Producto no encontrado"));
        detalleEditar.setCantidad(detalledto.getCantidad());
        detalleEditar.setProducto(producto);
        detalleRepo.save(detalleEditar);
    }
}
