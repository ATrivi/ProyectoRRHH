package com.example.proyectoRecursosHumanos.controller;


import com.example.proyectoRecursosHumanos.dto.UserRequest;
import com.example.proyectoRecursosHumanos.dto.UserResponse;
import com.example.proyectoRecursosHumanos.model.User;
import com.example.proyectoRecursosHumanos.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {

        this.userService = userService;
    }

    @PostMapping("/registro")
    public ResponseEntity<UserResponse> registrarUsuario(@Valid @RequestBody UserRequest req) {
        UserResponse res = userService.registrarUsuario(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @PostMapping("/registroAdmin")
    public ResponseEntity<UserResponse> registrarAdmin(@Valid @RequestBody UserRequest req) {
        UserResponse res = userService.registrarAdmin(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<User> cambiarContrasena(@PathVariable Integer id, @RequestBody User body) {

        User contraActualizada = userService.actualizarContrasena(id, body);
        return ResponseEntity.ok(contraActualizada);
    }

    @PutMapping("/{id}/editar")
    public ResponseEntity<User> editarUsuario(@PathVariable Integer id, @RequestBody UserResponse req) {

        User usuarioActualizado = userService.editarUsuario(id, req);
        return ResponseEntity.ok(usuarioActualizado);
    }

    @DeleteMapping("/{id}/borrar")
    public ResponseEntity<Void> borrarUsuario(@PathVariable Integer id) {

        userService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/info/{username}")
    public ResponseEntity<UserResponse> obtenerInfoUsuario(@PathVariable String username) {
        UserResponse info = userService.obtenerInfoUsuario(username);
        return ResponseEntity.ok(info);
    }
}


