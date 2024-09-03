package com.proyecto.ventas.proyectoventasbazar.repository;

import com.proyecto.ventas.proyectoventasbazar.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductoRepository extends JpaRepository<Producto,Long> {
}
