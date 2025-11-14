package com.example.proyectoRecursosHumanos.controller;

import com.example.proyectoRecursosHumanos.exception.ResourceNotFoundException;
import com.example.proyectoRecursosHumanos.model.Notification;
import com.example.proyectoRecursosHumanos.model.Employee;
import com.example.proyectoRecursosHumanos.repository.EmployeeRepository;
import com.example.proyectoRecursosHumanos.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/notificaciones")
public class NotificationController {

    private final NotificationRepository notificationRepository;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public NotificationController(NotificationRepository notificationRepository, EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
        this.notificationRepository = notificationRepository;
    }

    @GetMapping("/mi-bandeja")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Notification>> ObtenerNoLeidas(Principal principal) {

        Optional<Employee> employeeOptional = employeeRepository.findByUser_UserName(principal.getName());

        Employee employee = employeeOptional
                .orElseThrow(() -> new ResourceNotFoundException("Empleado", "Username", principal.getName()));

        List<Notification> notifications = notificationRepository
                .findByEmployeeAndIsReadFalseOrderByCreatedAtDesc(employee);

        return ResponseEntity.ok(notifications);
    }

    @PutMapping("/marcar-leidas")
    @PreAuthorize("isAuthenticated()")
    @Transactional // Necesario porque estamos ejecutando una consulta de modificación (UPDATE)
    public ResponseEntity<Void> marcarComoLeidas(Principal principal) {

        Optional<Employee> employeeOptional = employeeRepository.findByUser_UserName(principal.getName());

        Employee employee = employeeOptional
                .orElseThrow(() -> new ResourceNotFoundException("Empleado", "Username", principal.getName()));

        int updatedCount = notificationRepository.markAllAsRead(employee);

        return ResponseEntity.ok().build();
    }
}