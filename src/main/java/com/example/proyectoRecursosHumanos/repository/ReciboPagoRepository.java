package com.example.proyectoRecursosHumanos.repository;

import com.example.proyectoRecursosHumanos.model.ReciboPago;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReciboPagoRepository extends JpaRepository<ReciboPago, Integer> {

    // Buscar el recibo por el ID de la nómina
    ReciboPago findByPayrollId(Integer payrollId);
}