package com.example.proyectoRecursosHumanos.repository;

import com.example.proyectoRecursosHumanos.model.Payroll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PayrollRepository extends JpaRepository<Payroll, Integer> {

    //Con este parametro se extrae el mes y el año del localDate que tenemos en la entidad Payroll
    List<Payroll> findByFechaPagoBetween(LocalDate startDate, LocalDate endDate); // Para sacar reportes mensuales
    Optional<Payroll> findByEmpleadoId(Integer empleadoId);

}
