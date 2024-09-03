package com.proyecto.ventas.proyectoventasbazar.service;

import com.proyecto.ventas.proyectoventasbazar.model.Cliente;
import com.proyecto.ventas.proyectoventasbazar.model.Producto;

import java.util.List;

public interface IClienteService {

    public List<Cliente> getClientes();
    public Cliente findCliente(Long id);
    public void saveCliente(Cliente cliente);
    public void deleteCliente(Long id);
    public void editCliente(Cliente cliente);


}
