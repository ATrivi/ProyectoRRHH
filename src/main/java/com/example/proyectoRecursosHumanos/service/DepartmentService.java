package com.example.proyectoRecursosHumanos.service;

import com.example.proyectoRecursosHumanos.dto.DepartmentRequest;
import com.example.proyectoRecursosHumanos.dto.DepartmentResponse;
import com.example.proyectoRecursosHumanos.dto.EmployeeList;
import com.example.proyectoRecursosHumanos.exception.DuplicateResourceException;
import com.example.proyectoRecursosHumanos.exception.OperationConflictException;
import com.example.proyectoRecursosHumanos.exception.ResourceNotFoundException;
import com.example.proyectoRecursosHumanos.model.Department;
import com.example.proyectoRecursosHumanos.model.Employee;
import com.example.proyectoRecursosHumanos.repository.DepartmentRepository;
import com.example.proyectoRecursosHumanos.repository.EmployeeRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class DepartmentService {


    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;

    public DepartmentService(DepartmentRepository departmentRepository, EmployeeRepository employeeRepository) {
        this.departmentRepository = departmentRepository;
        this.employeeRepository = employeeRepository;
    }

    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public DepartmentResponse crearDepartamento(DepartmentRequest request) {

        Employee responsable = employeeRepository.findById(request.getResponsableId())
                .orElseThrow(() -> new ResourceNotFoundException("Empleado", "id", request.getResponsableId()));

        if (departmentRepository.existsByNombreDepartamento(request.getNombreDepartamento())) {
            throw new DuplicateResourceException("El departamento '" + request.getNombreDepartamento() + "' ya existe.");
        }

        var departamento = new Department();
        departamento.setNombreDepartamento(request.getNombreDepartamento());
        departamento.setResponsable(responsable);
        Department creado = departmentRepository.save(departamento);

        return new DepartmentResponse(
                creado.getId(),
                creado.getNombreDepartamento(),
                creado.getResponsable() != null ? creado.getResponsable().getNombre() : "N/A"
        );
    }

    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public DepartmentResponse editarDepartamento(Integer id, DepartmentRequest request) {

        Department departamento = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Departamento", "id", id));
        Employee responsable = employeeRepository.findById(request.getResponsableId())
                .orElseThrow(() -> new ResourceNotFoundException("Empleado", "id", request.getResponsableId()));

        departamento.setNombreDepartamento(request.getNombreDepartamento());
        departamento.setResponsable(responsable);
        Department editado = departmentRepository.save(departamento);

        return new DepartmentResponse(
                editado.getId(),
                editado.getNombreDepartamento(),
                editado.getResponsable() != null ? editado.getResponsable().getNombre() : "N/A"
        );

    }

    @Transactional(readOnly = true)
    @PreAuthorize("isAuthenticated()")
    public DepartmentResponse obtenerDepartamento(Integer id, DepartmentRequest request) {
        return departmentRepository.findById(id)
                .map(departamento -> new DepartmentResponse(
                        departamento.getId(),
                        departamento.getNombreDepartamento(),
                        departamento.getResponsable() != null ? departamento.getResponsable().getNombre() : "N/A"
                ))
                .orElseThrow(() -> new ResourceNotFoundException("Departamento", "id", id));
    }

    // DepartmentService.java

    @Transactional(readOnly = true)
    @PreAuthorize("isAuthenticated()")
    public List<EmployeeList> obtenerEmpleadosPorDepartamento(Integer departmentId) {

        if (!departmentRepository.existsById(departmentId)) {
            throw new ResourceNotFoundException("Departamento", "id", departmentId);
        }
        List<Employee> empleados = employeeRepository.findByDepartamento_Id(departmentId);
        List<EmployeeList> listaRespuesta = new ArrayList<>();

        for (Employee empleado : empleados) {
            EmployeeList simpleResponse = new EmployeeList(empleado);

            listaRespuesta.add(simpleResponse);
        }

        return listaRespuesta;
    }


    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public void eliminarDepartamento(Integer id) {
        if (!departmentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Departamento", "id", id);
        }

         boolean tieneEmpleados = employeeRepository.existsByDepartamento_Id(id);
            if (tieneEmpleados) {
                throw new OperationConflictException("No se puede eliminar: tiene empleados asociados");
            }
        departmentRepository.deleteById(id);
    }

}
