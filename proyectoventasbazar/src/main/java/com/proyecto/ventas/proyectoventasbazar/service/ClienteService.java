package com.proyecto.ventas.proyectoventasbazar.service;


import com.proyecto.ventas.proyectoventasbazar.model.Cliente;
import com.proyecto.ventas.proyectoventasbazar.repository.IClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService implements IClienteService {

    @Autowired
    IClienteRepository clienteRepo;

    @Override
    public List<Cliente> getClientes() {
        return clienteRepo.findAll();
    }

    @Override
    public Cliente findCliente(Long id) {
        return clienteRepo.findById(id).orElseThrow(()->new RuntimeException("Cliente no encontrado"));
    }

    @Override
    public void saveCliente(Cliente cliente) {
        clienteRepo.save(cliente);
    }

    @Override
    public void deleteCliente(Long id) {
        clienteRepo.deleteById(id);
    }

    @Override
    public void editCliente(Long id_cliente,Cliente cliente) {
        Cliente clienteEditar = this.findCliente(id_cliente);
        clienteEditar.setNombre(cliente.getNombre());
        clienteEditar.setApellido(cliente.getApellido());
        clienteEditar.setVentas(cliente.getVentas());
        clienteEditar.setDni(cliente.getDni());
        clienteRepo.save(clienteEditar);
    }
}
