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

/**
 * Servicio que implementa la lógica de negocio para la gestión de detalles de venta.
 * Proporciona métodos para realizar operaciones CRUD sobre la entidad DetalleVenta.
 */

@Service
public class DetalleVentaService implements IDetalleVentaService{

    @Autowired
    IDetalleVentaRepository detalleRepo;

    @Autowired
    IProductoRepository producRepo;

    /**
     * Obtiene la lista de todos los detalles de venta almacenados en la base de datos.
     * 
     * @return Lista de detalles de venta en formato DTO.
     * @throws ResourceNotFoundException Si no se encuentran detalles de venta registrados.
     */
    @Override
    public List<DetalleDTO> getDetalles() throws ResourceNotFoundException {
        List<DetalleDTO> detallesDTO = new ArrayList<>();
        List<DetalleVenta> detalles = detalleRepo.findAll();
        if(detalles == null || detalles.isEmpty()){
            throw new ResourceNotFoundException("Detalles no encontrados","P-404");
        }
        for(DetalleVenta detalle : detalles){
            DetalleDTO detalleDTO = new DetalleDTO(detalle.getIdDetalle(),detalle.getProducto().getCodigoProducto(),detalle.getCantidad(),detalle.getPrecio());
            detallesDTO.add(detalleDTO);
        }
        
        return detallesDTO;
    }

      /**
     * Busca un detalle de venta en la base de datos según su identificador único.
     * 
     * @param idDetalle Identificador del detalle de venta a buscar.
     * @return DetalleVenta encontrado.
     * @throws ResourceNotFoundException Si el detalle de venta no existe en la base de datos.
     */
    @Override
    public DetalleVenta findDetalle(Long idDetalle) throws ResourceNotFoundException {
        return detalleRepo.findById(idDetalle).orElseThrow(()-> new ResourceNotFoundException("Detalle con la id" + idDetalle + " no encontrado"
                                    ,"P-404"));
    }

    /**
     * Obtiene un detalle de venta en formato DTO según su identificador.
     * 
     * @param idDetalle Identificador del detalle de venta.
     * @return DetalleVenta en formato DTO.
     */
    @Override
    public DetalleDTO getDetalleDTO(Long idDetalle) {
        DetalleVenta detalleBuscar = findDetalle(idDetalle);
        DetalleDTO detalleDTO = new DetalleDTO(detalleBuscar.getIdDetalle(),detalleBuscar.getProducto().getCodigoProducto(),detalleBuscar.getCantidad(),detalleBuscar.getPrecio());
        return detalleDTO;
    }

    
    /**
     * Guarda un nuevo detalle de venta en la base de datos.
     * 
     * @param detalle Objeto DetalleVenta que se desea guardar.
     */
    @Override
    @Transactional
    public void saveDetalle(DetalleVenta detalle) {
        detalleRepo.save(detalle);
    }


    /**
     * Elimina un detalle de venta de la base de datos según su identificador único.
     * 
     * @param idDetalle Identificador del detalle de venta a eliminar.
     * @throws ResourceNotFoundException Si el detalle de venta no existe en la base de datos.
     */
    @Override
    @Transactional
    public void deleteDetalle(Long idDetalle) throws ResourceNotFoundException {
        if(!detalleRepo.existsById(idDetalle)){
            throw new ResourceNotFoundException("Detalle con la id " + idDetalle + " no encontrado", "P-404");
        }
        detalleRepo.deleteById(idDetalle);
    }

    
    /**
     * Modifica los datos de un detalle de venta existente en la base de datos.
     * 
     * @param idDetalle Identificador del detalle de venta a editar.
     * @param detalledto Objeto DetalleDTO con los nuevos datos.
     */
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
