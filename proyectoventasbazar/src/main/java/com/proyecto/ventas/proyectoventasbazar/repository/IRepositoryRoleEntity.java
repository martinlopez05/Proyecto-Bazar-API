package com.proyecto.ventas.proyectoventasbazar.repository;

import com.proyecto.ventas.proyectoventasbazar.model.EnumRole;
import com.proyecto.ventas.proyectoventasbazar.model.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRepositoryRoleEntity extends JpaRepository<RoleEntity,Long> {

    Optional<RoleEntity> findByName(EnumRole name);
}
