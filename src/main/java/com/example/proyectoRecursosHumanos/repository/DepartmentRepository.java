package com.example.proyectoRecursosHumanos.repository;

import com.example.proyectoRecursosHumanos.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

    public interface DepartmentRepository extends JpaRepository<Department, Integer> {

        Optional<Department> findByNombreDepartamento(String nombre);

        boolean existsByNombreDepartamento(String nombreDepartamento);
    }

