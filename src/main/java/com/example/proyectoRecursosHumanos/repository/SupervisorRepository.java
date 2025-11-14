package com.example.proyectoRecursosHumanos.repository;

import com.example.proyectoRecursosHumanos.model.Supervisor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SupervisorRepository extends JpaRepository<Supervisor, Integer> {

    Optional<Supervisor> findBySubordinado_IdAndFechaFinIsNull(Integer subordinadoId);

    List<Supervisor> findAllBySubordinado_IdOrderByFechaInicioDesc(Integer subordinadoId);

    List<Supervisor> findAllBySupervisor_IdAndFechaFinIsNull(Integer supervisorId);
}

