package com.example.proyectoRecursosHumanos.service;

import com.example.proyectoRecursosHumanos.dto.NotificationResponse;
import com.example.proyectoRecursosHumanos.model.Employee;
import com.example.proyectoRecursosHumanos.model.Notification;
import com.example.proyectoRecursosHumanos.repository.NotificationRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public NotificationResponse crearNotificacion (Employee employee, String message, String type) {
        Notification notification = new Notification();
        notification.setEmployee(employee);
        notification.setMessage(message);
        notification.setType(type);
        notification.setIsRead(false);
        notification.setCreatedAt(LocalDateTime.now());

        Notification notificationNueva = notificationRepository.save(notification);


        return new NotificationResponse(notificationNueva);
    }
}
