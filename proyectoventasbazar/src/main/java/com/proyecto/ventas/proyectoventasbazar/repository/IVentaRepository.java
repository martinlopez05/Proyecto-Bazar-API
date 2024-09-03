package com.proyecto.ventas.proyectoventasbazar.repository;

import com.proyecto.ventas.proyectoventasbazar.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IVentaRepository extends JpaRepository <Venta,Long> {
}
