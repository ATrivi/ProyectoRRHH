package com.example.proyectoRecursosHumanos.repository;

import com.example.proyectoRecursosHumanos.model.Employee;
import com.example.proyectoRecursosHumanos.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {

    /**
     * Recupera las últimas notificaciones NO leídas para un empleado, ordenadas por fecha descendente.
     */
    List<Notification> findByEmployeeAndIsReadFalseOrderByCreatedAtDesc(Employee employee);

    /**
     * Marca como leídas todas las notificaciones pendientes de un empleado.
     * Requiere @Modifying y @Transactional para ejecutar una consulta de actualización.
     */
    @Modifying
    @Query("UPDATE Notification n SET n.isRead = TRUE WHERE n.employee = :employee AND n.isRead = FALSE")
    int markAllAsRead(@Param("employee") Employee employee);
}

