package com.example.proyectoRecursosHumanos.service;


import com.example.proyectoRecursosHumanos.dto.UserRequest;
import com.example.proyectoRecursosHumanos.dto.UserResponse;
import com.example.proyectoRecursosHumanos.exception.OperationConflictException;
import com.example.proyectoRecursosHumanos.exception.ResourceNotFoundException;
import com.example.proyectoRecursosHumanos.model.User;
import com.example.proyectoRecursosHumanos.repository.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public UserResponse registrarUsuario(UserRequest req) {
        if (userRepository.existsByUserName(req.getUserName())) {
            throw new OperationConflictException("El nombre de usuario '" + req.getUserName() + "' ya está registrado");
        }

        User user = new User();
        user.setUserName(req.getUserName());
        user.setEmail(req.getEmail());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setRole("USER");
        userRepository.save(user);

        return new UserResponse(user.getId(),
                user.getUserName(), user.getRole(), user.getEmail());
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse registrarAdmin(UserRequest req) {

        if (userRepository.existsByUserName(req.getUserName())) {
            throw new OperationConflictException("El nombre de usuario '" + req.getUserName() + "' ya está registrado");
        } else {

            User user = new User();
            user.setUserName(req.getUserName());
            user.setEmail(req.getEmail());
            user.setPassword(passwordEncoder.encode(req.getPassword()));
            user.setRole("ADMIN");

            userRepository.save(user);

            UserResponse res = new UserResponse(
                    user.getId(),
                    user.getUserName(),
                    user.getRole(),
                    user.getEmail()
            );
            return res;
        }
    }

    @Transactional
    @PreAuthorize("isAuthenticated()")
    public User actualizarContrasena(Integer id, User nuevaContrasena) {

        User u = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", id));

            u.setPassword(passwordEncoder.encode(nuevaContrasena.getPassword()));
            return userRepository.save(u);

    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public User editarUsuario(Integer id, UserResponse userActualizado) {

        User u = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", id));

            u.setUserName(userActualizado.getUserName());;
            u.setEmail(userActualizado.getEmail());
            u.setRole(userActualizado.getRole());

            return userRepository.save(u);
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public void eliminarUsuario(Integer id) {

        User u = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", id));

        userRepository.deleteById(id);

    }

    @PreAuthorize("isAuthenticated()")
    public UserResponse obtenerInfoUsuario(String userName) {

        //Usamos el constructor user del DTO userResponse para devolver la info
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "Username", userName));

        return new UserResponse(
                user.getId(),
                user.getUserName(),
                user.getRole(),
                user.getEmail()
        );
    }
}
