package com.example.proyectoRecursosHumanos.controller;

import com.example.proyectoRecursosHumanos.dto.NotificationRequest;
import com.example.proyectoRecursosHumanos.dto.NotificationResponse;
import com.example.proyectoRecursosHumanos.exception.ResourceNotFoundException;
import com.example.proyectoRecursosHumanos.model.Notification;
import com.example.proyectoRecursosHumanos.model.Employee;
import com.example.proyectoRecursosHumanos.repository.EmployeeRepository;
import com.example.proyectoRecursosHumanos.repository.NotificationRepository;
import com.example.proyectoRecursosHumanos.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/notificaciones")
public class NotificationController {

    private final NotificationRepository notificationRepository;
    private final EmployeeRepository employeeRepository;
    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationRepository notificationRepository, EmployeeRepository employeeRepository, NotificationService notificationService) {
        this.notificationService = notificationService;
        this.employeeRepository = employeeRepository;
        this.notificationRepository = notificationRepository;
    }

    @PostMapping("/crear")
    public ResponseEntity<NotificationResponse> crearNotificacion(@RequestBody NotificationRequest request) {
        Employee employee = employeeRepository.findByUser_UserName(request.getUserName())
                .orElseThrow(() -> new ResourceNotFoundException("Empleado", "Username", request.getUserName()));

        NotificationResponse notificacionNueva = notificationService.crearNotificacion(employee, request.getMessage(), request.getType());

        return ResponseEntity.status(HttpStatus.CREATED).body(notificacionNueva);
    }

    @GetMapping("/mi-bandeja")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<NotificationResponse>> ObtenerNoLeidas(Principal principal) {

        Optional<Employee> employeeOptional = employeeRepository.findByUser_UserName(principal.getName());

        Employee employee = employeeOptional
                .orElseThrow(() -> new ResourceNotFoundException("Empleado", "Username", principal.getName()));

        List<Notification> notifications = notificationRepository
                .findByEmployeeAndIsReadFalseOrderByCreatedAtDesc(employee);

        List<NotificationResponse> responseList = notifications.stream()
                .map(NotificationResponse::new) // Utiliza el constructor de DTO(Entidad)
                .collect(java.util.stream.Collectors.toList());

        //Devolver la lista de DTOs
        return ResponseEntity.ok(responseList);
    }

    @PutMapping("/marcar-leidas")
    @PreAuthorize("isAuthenticated()")
    @Transactional
    public ResponseEntity<Void> marcarComoLeidas(Principal principal) {

        //Principal principal es un parámetro que Spring MVC inyecta automáticamente con el principal de seguridad actual (implementa java.security.Principal).
        // En aplicaciones con Spring Security, principal.getName() suele devolver el nombre de usuario autenticado

        Optional<Employee> employeeOptional = employeeRepository.findByUser_UserName(principal.getName());

        Employee employee = employeeOptional
                .orElseThrow(() -> new ResourceNotFoundException("Empleado", "Username", principal.getName()));

        int updatedCount = notificationRepository.markAllAsRead(employee);

        return ResponseEntity.ok().build();
    }
}