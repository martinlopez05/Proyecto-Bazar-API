package com.proyecto.ventas.proyectoventasbazar.service;

import com.proyecto.ventas.proyectoventasbazar.model.UserDetaisImpl;
import com.proyecto.ventas.proyectoventasbazar.model.UserEntity;
import com.proyecto.ventas.proyectoventasbazar.repository.IRepositoryUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    IRepositoryUserEntity repositoryUserEntity;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = repositoryUserEntity.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username not found"));

        return UserDetaisImpl.builder()
                        .userEntity(userEntity)
                        .build();

    }
}
