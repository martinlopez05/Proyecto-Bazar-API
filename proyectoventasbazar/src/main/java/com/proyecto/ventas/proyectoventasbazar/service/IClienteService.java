package com.proyecto.ventas.proyectoventasbazar.service;

import com.proyecto.ventas.proyectoventasbazar.model.Cliente;
import com.proyecto.ventas.proyectoventasbazar.model.Producto;

import java.util.List;

public interface IClienteService {

    public List<Cliente> getClientes();
    public Cliente findCliente(Long idCliente);
    public void saveCliente(Cliente cliente);
    public void deleteCliente(Long idCliente);
    public void editCliente(Long idCliente,Cliente cliente);


}
