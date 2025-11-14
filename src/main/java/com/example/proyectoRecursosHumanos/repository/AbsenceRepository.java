package com.example.proyectoRecursosHumanos.repository;

import com.example.proyectoRecursosHumanos.model.Absence;
import com.example.proyectoRecursosHumanos.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AbsenceRepository extends JpaRepository<Absence, Integer> {

    Optional<Absence> findByTipoAusencia(String tipoAusencia);

    Boolean existsByTipoAusencia(String tipoAusencia);

    List<Absence> findByEmpleado(Employee empleado);

}
