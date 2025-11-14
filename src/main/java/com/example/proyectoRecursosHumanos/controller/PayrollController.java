package com.example.proyectoRecursosHumanos.controller;

import com.example.proyectoRecursosHumanos.dto.PayrollRequest;
import com.example.proyectoRecursosHumanos.dto.PayrollResponse;
import com.example.proyectoRecursosHumanos.service.PayrollService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/nominas")
public class PayrollController {

    private final PayrollService payrollService;

    public PayrollController(PayrollService payrollService) {
        this.payrollService = payrollService;
    }

    @PostMapping("/crearNomina/{userName}")
    public ResponseEntity<PayrollResponse> crearNomina(@Valid @RequestBody PayrollRequest PayrollRequest, @PathVariable String userName) {
        PayrollResponse response = payrollService.crearNomina(PayrollRequest, userName);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/informe-mensual")
    public ResponseEntity<List<PayrollResponse>> obtenerInformeMensual(@RequestParam int year, @RequestParam int month) {
        List<PayrollResponse> informe = payrollService.obtenerNominasPorMes(year, month);
        return ResponseEntity.ok(informe);
    }

    @GetMapping("/recibo/{payrollId}/descargar")
    public ResponseEntity<byte[]> descargarRecibo(@PathVariable Integer payrollId) {

        byte[] pdfContent = payrollService.descargarReciboPago(payrollId);

        String filename = "ReciboNomina_" + payrollId + ".pdf";

        //Construye la respuesta con las cabeceras
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .contentType(MediaType.APPLICATION_PDF) // Indicar que el contenido es un PDF
                .body(pdfContent);
    }

}
