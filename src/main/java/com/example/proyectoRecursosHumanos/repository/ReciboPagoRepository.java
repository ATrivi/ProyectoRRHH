package com.example.proyectoRecursosHumanos.repository;

import com.example.proyectoRecursosHumanos.model.ReciboPago;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReciboPagoRepository extends JpaRepository<ReciboPago, Integer> {

    ReciboPago findByPayrollId(Integer payrollId);
}