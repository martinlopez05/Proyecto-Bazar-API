package com.proyecto.ventas.proyectoventasbazar.repository;

import com.proyecto.ventas.proyectoventasbazar.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRepositoryUserEntity extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username);

}
