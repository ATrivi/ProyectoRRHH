package com.example.proyectoRecursosHumanos.repository;

import com.example.proyectoRecursosHumanos.model.JobPosition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JobPositionRepository extends JpaRepository<JobPosition, Integer> {

    // Se evitan duplicados al crear/editar
    boolean existsByNombrePuesto(String nombrePuesto);

    Optional<JobPosition> findByNombrePuesto(String nombrePuesto);
}