package com.proyecto.ventas.proyectoventasbazar.service;


import com.proyecto.ventas.proyectoventasbazar.model.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService implements IClienteService {

    @Autowired
    IClienteService clienteRepo;

    @Override
    public List<Cliente> getClientes() {
        return clienteRepo.getClientes();
    }

    @Override
    public Cliente findCliente(Long id) {
        return clienteRepo.findCliente(id);
    }

    @Override
    public void saveCliente(Cliente cliente) {
        clienteRepo.saveCliente(cliente);
    }

    @Override
    public void deleteCliente(Long id) {
        clienteRepo.deleteCliente(id);
    }

    @Override
    public void editCliente(Cliente cliente) {
       clienteRepo.saveCliente(cliente);
    }
}
