package com.proyecto.ventas.proyectoventasbazar.repository;

import com.proyecto.ventas.proyectoventasbazar.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IClienteRepository extends JpaRepository<Cliente,Long> {
}
