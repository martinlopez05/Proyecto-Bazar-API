package com.proyecto.ventas.proyectoventasbazar.config.filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.proyecto.ventas.proyectoventasbazar.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;

public class JwtTokenValidator extends OncePerRequestFilter {


    private final JwtUtil jwtUtils;

    public JwtTokenValidator(JwtUtil jwtUtils) {
            this.jwtUtils = jwtUtils;
        }


        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
            String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION);

            if(jwtToken != null){
                jwtToken = jwtToken.substring(7);

                DecodedJWT decodedJWT = jwtUtils.validateToken(jwtToken);

                String username = jwtUtils.extractUsername(decodedJWT);
                String stringRole = jwtUtils.getSpecificClaim(decodedJWT,"role").asString();

                Collection<? extends GrantedAuthority> roles = AuthorityUtils.commaSeparatedStringToAuthorityList(stringRole);

                SecurityContext context = SecurityContextHolder.getContext();
                Authentication authentication = new UsernamePasswordAuthenticationToken(username, null,roles);
                context.setAuthentication(authentication);

                SecurityContextHolder.setContext(context);

            }

            filterChain.doFilter(request,response);
        }

}
