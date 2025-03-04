package com.proyecto.ventas.proyectoventasbazar.service;

import com.proyecto.ventas.proyectoventasbazar.exceptions.ResourceNotFoundException;
import com.proyecto.ventas.proyectoventasbazar.model.Cliente;
import com.proyecto.ventas.proyectoventasbazar.repository.IClienteRepository;
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
    public List<Cliente> getClientes() {
        return clienteRepo.findAll();
    }

    @Override
    public Cliente findCliente(Long idCliente) throws ResourceNotFoundException {
        Optional<Cliente> cliente = clienteRepo.findById(idCliente);
        if(!cliente.isPresent()){
            throw new ResourceNotFoundException("Cliente no encontrado", "P-404");
        }
        return cliente.get();
              
    }
    
    

    @Override
    public void saveCliente(Cliente cliente) {
        clienteRepo.save(cliente);
    }

    @Override
    public void deleteCliente(Long idCliente) {
        clienteRepo.deleteById(idCliente);
    }

    @Override
    public void editCliente(Long idCliente, Cliente cliente) {
        Cliente clienteEditar = this.findCliente(idCliente);
        clienteEditar.setNombre(cliente.getNombre());
        clienteEditar.setApellido(cliente.getApellido());
        clienteEditar.setVentas(cliente.getVentas());
        clienteEditar.setDni(cliente.getDni());
        clienteRepo.save(clienteEditar);
    }
   
}
