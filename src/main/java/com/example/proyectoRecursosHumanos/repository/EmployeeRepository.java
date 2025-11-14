package com.example.proyectoRecursosHumanos.repository;

import com.example.proyectoRecursosHumanos.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface EmployeeRepository extends JpaRepository <Employee, Integer> {

    Optional<Employee> findByUser_UserName (String userName);

    boolean existsByUserId(Integer userId);

    Optional<Employee> findByUser_Email (String email);

    boolean existsByDepartamento_Id(Integer departmentId);

    List<Employee> findByDepartamento_Id(Integer departmentId);

}
