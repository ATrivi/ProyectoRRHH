package com.example.proyectoRecursosHumanos.service;

import com.example.proyectoRecursosHumanos.dto.EmployeeRequest;
import com.example.proyectoRecursosHumanos.dto.EmployeeResponse;
import com.example.proyectoRecursosHumanos.exception.OperationConflictException;
import com.example.proyectoRecursosHumanos.exception.ResourceNotFoundException;
import com.example.proyectoRecursosHumanos.model.Employee;
import com.example.proyectoRecursosHumanos.repository.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final JobPositionRepository jobPositionRepository;
    private final UserRepository userRepository;

    public EmployeeService(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository, JobPositionRepository jobPositionRepository, UserRepository userRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.jobPositionRepository = jobPositionRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public EmployeeResponse registrarEmpleado(EmployeeRequest req, String userName) {

        //Busca el usuario por su username para asignarle el empleado
        var user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "Username", userName));

        //Revisa que este usuario no tenga ya un empleado asignado
        if (employeeRepository.existsByUserId(user.getId())) {
            throw new OperationConflictException("Este usuario ya tiene un empleado registrado");
        }

        //Carga el departamento y el puesto por su ID
        var dept = departmentRepository.findById(req.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Departamento", "id", req.getDepartmentId()));

        var puesto = jobPositionRepository.findById(req.getJobPositionId())
                .orElseThrow(() -> new ResourceNotFoundException("Puesto", "id", req.getJobPositionId()));

        var empleado = new Employee();

        empleado.setUser(user);
        empleado.setNombre(req.getNombre());
        empleado.setApellidos(req.getApellidos());
        empleado.setDepartamento(dept);
        empleado.setPuesto(puesto);
        empleado.setEstado(req.getEstado());
        empleado.setSalario(req.getSalario());

        var guardado = employeeRepository.save(empleado);

        return new EmployeeResponse(guardado);

    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public Employee editarEmpleado(Integer id, Employee employeeActualizado) {

        Employee e = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empleado", "id", id));

        e.setNombre(employeeActualizado.getNombre());
        e.setApellidos(employeeActualizado.getApellidos());
        e.setSalario(employeeActualizado.getSalario());
        e.setPuesto(employeeActualizado.getPuesto());
        e.setDepartamento(employeeActualizado.getDepartamento());
        e.setEstado(employeeActualizado.getEstado());

        return employeeRepository.save(e);
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public boolean eliminarEmpleado(Integer id) {

        employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empleado", "id", id));

        employeeRepository.deleteById(id);

        return true;
    }

    @Transactional(readOnly = true)
    @PreAuthorize("isAuthenticated()")
    public EmployeeResponse obtenerInfoEmpleado(String userName) {
        Employee employee = employeeRepository.findByUser_UserName(userName)
                .orElseThrow(() -> new ResourceNotFoundException("Empleado", "id", userName));
        return new EmployeeResponse(employee);
    }
}
