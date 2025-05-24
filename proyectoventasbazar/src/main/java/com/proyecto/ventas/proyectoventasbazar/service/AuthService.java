package com.proyecto.ventas.proyectoventasbazar.service;

import com.proyecto.ventas.proyectoventasbazar.dto.AuthResponseDTO;
import com.proyecto.ventas.proyectoventasbazar.dto.AuthRequestDTO;
import com.proyecto.ventas.proyectoventasbazar.dto.RegisterRequestDTO;
import com.proyecto.ventas.proyectoventasbazar.dto.RegisterResponseDTO;
import com.proyecto.ventas.proyectoventasbazar.model.EnumRole;
import com.proyecto.ventas.proyectoventasbazar.model.RoleEntity;
import com.proyecto.ventas.proyectoventasbazar.model.UserEntity;
import com.proyecto.ventas.proyectoventasbazar.repository.IRepositoryRoleEntity;
import com.proyecto.ventas.proyectoventasbazar.repository.IRepositoryUserEntity;
import com.proyecto.ventas.proyectoventasbazar.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    IRepositoryUserEntity repositoryUserEntity;

    @Autowired
    UserDetailServiceImpl userDetailService;

    @Autowired
    IRepositoryRoleEntity roleRepository;

    private final JwtUtil jwtUtil;

    private final PasswordEncoder passwordEncoder;

    public AuthService(JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthResponseDTO login(AuthRequestDTO authRequestDTO){
        UserDetails userDetails = userDetailService.loadUserByUsername(authRequestDTO.getUsername());

        if(!passwordEncoder.matches(authRequestDTO.getPassword(), userDetails.getPassword())){
            throw new RuntimeException("Incorrect password");
        }

        String role = userDetails.getAuthorities().iterator().next().getAuthority();

        String token = jwtUtil.generateToken(userDetails.getUsername(),role);

        return AuthResponseDTO.builder()
                .token(token)
                .build();

    }

    public RegisterResponseDTO register(RegisterRequestDTO registerRequestDTO){
        String username = registerRequestDTO.getUsername();
        String password = registerRequestDTO.getPassword();
        String role = (registerRequestDTO.getRole() != null && !registerRequestDTO.getRole().isEmpty())
                ? registerRequestDTO.getRole().toUpperCase()
                : "USER";


        Optional<RoleEntity> roleOpt = roleRepository.findByName(EnumRole.valueOf(role));

        if (roleOpt.isEmpty()) {
            throw new RuntimeException("Error: Role " + role + " not found in the database.");
        }

        UserEntity user = UserEntity.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .role(roleOpt.get())
                .build();

        repositoryUserEntity.save(user);

        return RegisterResponseDTO.builder()
                .message("User registered successfully!")
                .build();


    }

}
