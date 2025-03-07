package com.proyecto.ventas.proyectoventasbazar.service;

import com.proyecto.ventas.proyectoventasbazar.exceptions.InvalidArgumentException;
import com.proyecto.ventas.proyectoventasbazar.exceptions.ResourceNotFoundException;
import com.proyecto.ventas.proyectoventasbazar.model.Cliente;
import com.proyecto.ventas.proyectoventasbazar.model.Producto;

import java.util.List;

public interface IClienteService {

    public List<Cliente> getClientes() throws ResourceNotFoundException ;
    public Cliente findCliente(Long idCliente) throws ResourceNotFoundException;
    public void saveCliente(Cliente cliente);
    public void deleteCliente(Long idCliente) throws ResourceNotFoundException;
    public void editCliente(Long idCliente,Cliente cliente);


}
