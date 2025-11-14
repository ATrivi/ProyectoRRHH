package com.example.proyectoRecursosHumanos.controller;


import com.example.proyectoRecursosHumanos.dto.AbsenceRequest;
import com.example.proyectoRecursosHumanos.dto.AbsenceResponse;
import com.example.proyectoRecursosHumanos.model.EstadoAusencia;
import com.example.proyectoRecursosHumanos.service.AbsenceService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ausencias")
public class AbsenceController {

    private final AbsenceService AbsenceService;

    public AbsenceController(AbsenceService absenceService) {
        AbsenceService = absenceService;
    }

    @PostMapping ("/registro/{userName}")
    public ResponseEntity<AbsenceResponse> registrarAusencia(@Valid @RequestBody AbsenceRequest absenceRequest, @PathVariable String userName) {
        AbsenceResponse response = AbsenceService.registrarAusencia(absenceRequest, userName);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping ("/{id}/aprobar")
    public ResponseEntity<AbsenceResponse> aprobarAusencia(@PathVariable Integer id) {
        AbsenceResponse response = AbsenceService.actualizarEstado(id, EstadoAusencia.APROBADA);
        return ResponseEntity.ok(response);
    }

    @PutMapping ("/{id}/rechazar")
    public ResponseEntity<AbsenceResponse> rechazarAusencia(@PathVariable Integer id) {
        AbsenceResponse response = AbsenceService.actualizarEstado(id, EstadoAusencia.RECHAZADA);
        return ResponseEntity.ok(response);
    }

    @GetMapping ("/empleado/{employeeId}")
    public ResponseEntity<List<AbsenceResponse>> obtenerAusenciasPorEmpleado(@PathVariable Integer employeeId) {
        List<AbsenceResponse> absences = AbsenceService.obtenerAusenciasPorEmpleado(employeeId);
        return ResponseEntity.ok(absences);
    }

}
