package com.proyecto.ventas.proyectoventasbazar.config;

import com.proyecto.ventas.proyectoventasbazar.model.EnumRole;
import com.proyecto.ventas.proyectoventasbazar.model.RoleEntity;
import com.proyecto.ventas.proyectoventasbazar.repository.IRepositoryRoleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RoleDataInitializer implements CommandLineRunner {

    @Autowired
    private IRepositoryRoleEntity roleRepository;

    @Override
    public void run(String... args) throws Exception {
        if (roleRepository.count() == 0) {
            roleRepository.save(RoleEntity.builder().name(EnumRole.ADMIN).build());
            roleRepository.save(RoleEntity.builder().name(EnumRole.USER).build());
            roleRepository.save(RoleEntity.builder().name(EnumRole.INVITED).build());
            System.out.println("Roles iniciales insertados");
        }
    }
}
