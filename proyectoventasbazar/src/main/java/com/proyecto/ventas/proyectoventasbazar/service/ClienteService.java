package com.proyecto.ventas.proyectoventasbazar.service;

import com.proyecto.ventas.proyectoventasbazar.exceptions.EmptyListException;
import com.proyecto.ventas.proyectoventasbazar.exceptions.ExceptionUtils;
import com.proyecto.ventas.proyectoventasbazar.exceptions.InvalidArgumentException;
import com.proyecto.ventas.proyectoventasbazar.exceptions.ResourceNotFoundException;
import com.proyecto.ventas.proyectoventasbazar.model.Cliente;
import com.proyecto.ventas.proyectoventasbazar.repository.IClienteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;

/**
 * Servicio que implementa la lógica de negocio para la gestión de clientes.
 * Proporciona métodos para realizar operaciones CRUD sobre la entidad Cliente.
 */
@Service
public class ClienteService implements IClienteService {

    @Autowired
    IClienteRepository clienteRepo;

    /**
     * Obtiene la lista de todos los clientes almacenados en la base de datos.
     *
     * @return Lista de clientes.
     * @throws EmptyListException Si no hay clientes registrados.
     */
    @Override
    public List<Cliente> getClientes() throws EmptyListException {
        List<Cliente> clientes = clienteRepo.findAll();
        ExceptionUtils.validateListNotEmpty(clientes, "No se han cargado clientes al sistema");

        return clientes;
    }

    /**
     * Busca un cliente en la base de datos según su identificador único.
     *
     * @param idCliente Identificador del cliente a buscar.
     * @return Cliente encontrado.
     * @throws ResourceNotFoundException Si el cliente no existe en la base de
     * datos.
     */
    @Override
    public Cliente findCliente(Long idCliente) throws ResourceNotFoundException {
        Optional<Cliente> cliente = clienteRepo.findById(idCliente);
        if (!cliente.isPresent()) {
            throw new ResourceNotFoundException("Cliente con la id " + idCliente + " no encontrado", "P-404");
        }
        return cliente.get();

    }

    /**
     * Guarda un nuevo cliente en la base de datos.
     *
     * @param cliente Objeto Cliente que se desea guardar.
     */
    @Override
    @Transactional
    public void saveCliente(Cliente cliente) {
        clienteRepo.save(cliente);
    }

    /**
     * Elimina un cliente de la base de datos según su identificador único.
     *
     * @param idCliente Identificador del cliente a eliminar.
     * @throws ResourceNotFoundException Si el cliente no existe en la base de
     * datos.
     */
    @Override
    @Transactional
    public void deleteCliente(Long idCliente) throws ResourceNotFoundException {
        if (!clienteRepo.existsById(idCliente)) {
            throw new ResourceNotFoundException("Cliente con la id " + idCliente + " no encontrado", "P-404");
        }
        clienteRepo.deleteById(idCliente);
    }

    /**
     * Modifica los datos de un cliente existente en la base de datos.
     *
     * @param idCliente Identificador del cliente a editar.
     * @param cliente Objeto Cliente con los nuevos datos.
     */
    @Override
    @Transactional
    public void editCliente(Long idCliente, Cliente cliente) {
        Cliente clienteEditar = this.findCliente(idCliente);
        
        if (cliente.getNombre() != null) {
            clienteEditar.setNombre(cliente.getNombre());
        }
        if (cliente.getApellido() != null) {
            clienteEditar.setApellido(cliente.getApellido());
        }
        if (cliente.getVentas() != null) {
            clienteEditar.setVentas(cliente.getVentas());
        }
        if (cliente.getDni() != null) {
            clienteEditar.setDni(cliente.getDni());
        }

        clienteRepo.save(clienteEditar);
    }

}
