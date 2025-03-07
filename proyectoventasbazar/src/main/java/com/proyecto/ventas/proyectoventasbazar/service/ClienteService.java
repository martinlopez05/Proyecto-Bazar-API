package com.proyecto.ventas.proyectoventasbazar.service;

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

@Service
public class ClienteService implements IClienteService {

    @Autowired
    IClienteRepository clienteRepo;

    @Override
    public List<Cliente> getClientes() throws ResourceNotFoundException {
        List<Cliente> clientes = clienteRepo.findAll();
        ExceptionUtils.validateListNotEmpty(clientes, "No se han cargado clientes al sistema");
        
        return clientes;
    }

    @Override
    public Cliente findCliente(Long idCliente) throws ResourceNotFoundException {
        Optional<Cliente> cliente = clienteRepo.findById(idCliente);
        if(!cliente.isPresent()){
            throw new ResourceNotFoundException("Cliente con la id " + idCliente + " no encontrado" , "P-404");
        }
        return cliente.get();
              
    }
    

    @Override
    @Transactional
    public void saveCliente(Cliente cliente) {
        clienteRepo.save(cliente);
    }

    @Override
    @Transactional
    public void deleteCliente(Long idCliente) throws ResourceNotFoundException {
        if(!clienteRepo.existsById(idCliente)){
              throw new ResourceNotFoundException("Cliente con la id " + idCliente +  " no encontrado", "P-404");
        }
        clienteRepo.deleteById(idCliente);
    }

    @Override
    @Transactional
    public void editCliente(Long idCliente, Cliente cliente) {
        Cliente clienteEditar = this.findCliente(idCliente);
        clienteEditar.setNombre(cliente.getNombre());
        clienteEditar.setApellido(cliente.getApellido());
        clienteEditar.setVentas(cliente.getVentas());
        clienteEditar.setDni(cliente.getDni());
        clienteRepo.save(clienteEditar);
    }
    
    
}
