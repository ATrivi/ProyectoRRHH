package com.example.proyectoRecursosHumanos.security;

import com.example.proyectoRecursosHumanos.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository; //Inyección de dependencias por constructor

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String nombreUsuario) throws UsernameNotFoundException {
        com.example.proyectoRecursosHumanos.model.User usuario =
                userRepository.findByUserName(nombreUsuario)
                        .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        return org.springframework.security.core.userdetails.User
                .withUsername(usuario.getUserName())
                .password(usuario.getPassword())
                .roles(usuario.getRole())
                .build();
    }
}
