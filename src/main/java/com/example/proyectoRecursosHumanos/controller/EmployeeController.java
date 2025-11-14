package com.example.proyectoRecursosHumanos.controller;


import com.example.proyectoRecursosHumanos.dto.EmployeeRequest;
import com.example.proyectoRecursosHumanos.dto.EmployeeResponse;
import com.example.proyectoRecursosHumanos.model.Employee;
import com.example.proyectoRecursosHumanos.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/empleados")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/registro/{userName}")
    public ResponseEntity<EmployeeResponse> registrarEmpleado(@Valid @RequestBody EmployeeRequest req, @PathVariable String userName) {
        EmployeeResponse res = employeeService.registrarEmpleado(req, userName);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @PutMapping ("/{id}/editar")
    public ResponseEntity<Employee> editarEmpleado(@PathVariable Integer id, @Valid @RequestBody Employee req) {
        Employee empleadoActualizado = employeeService.editarEmpleado(id, req);
        return ResponseEntity.ok(empleadoActualizado);
    }

    @DeleteMapping ("/{id}/eliminar")
    public ResponseEntity<Void> eliminarEmpleado(@PathVariable Integer id) {
        boolean eliminado = employeeService.eliminarEmpleado(id);

        if (eliminado) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/info/{userName}")
    public ResponseEntity<EmployeeResponse> obtenerInfoEmpleado(@PathVariable String userName) {

        EmployeeResponse info = employeeService.obtenerInfoEmpleado(userName);
        return ResponseEntity.ok(info);
    }

}

