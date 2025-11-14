package com.example.proyectoRecursosHumanos.controller;


import com.example.proyectoRecursosHumanos.dto.DepartmentRequest;
import com.example.proyectoRecursosHumanos.dto.DepartmentResponse;
import com.example.proyectoRecursosHumanos.dto.EmployeeList;
import com.example.proyectoRecursosHumanos.repository.EmployeeRepository;
import com.example.proyectoRecursosHumanos.service.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/departamentos")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping
    public ResponseEntity<DepartmentResponse> crearDepartamento(@Valid @RequestBody DepartmentRequest request) {
        DepartmentResponse response = departmentService.crearDepartamento(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}/editar")
    public ResponseEntity<DepartmentResponse> editarDepartamento(@PathVariable Integer id, @RequestBody DepartmentRequest req) {
        DepartmentResponse editado = departmentService.editarDepartamento(id, req);
        return ResponseEntity.ok(editado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentResponse> obtenerDepartamento(@PathVariable Integer id,@RequestBody DepartmentRequest req) {
        DepartmentResponse departamento = departmentService.obtenerDepartamento(id, req);
        return ResponseEntity.ok(departamento);
    }

    @DeleteMapping("/{id}/eliminar")
    public ResponseEntity<Void> eliminarDepartamento(@PathVariable Integer id) {
        departmentService.eliminarDepartamento(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{departmentId}/empleados")
    public ResponseEntity<List<EmployeeList>> obtenerEmpleadosPorDepartamento(@PathVariable Integer departmentId) {
        List<EmployeeList> empleados = departmentService.obtenerEmpleadosPorDepartamento(departmentId);
        return ResponseEntity.ok(empleados);
    }


}