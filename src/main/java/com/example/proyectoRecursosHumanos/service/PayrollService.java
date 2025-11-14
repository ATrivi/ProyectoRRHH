package com.example.proyectoRecursosHumanos.service;

import com.example.proyectoRecursosHumanos.dto.PayrollRequest;
import com.example.proyectoRecursosHumanos.dto.PayrollResponse;
import com.example.proyectoRecursosHumanos.exception.ResourceNotFoundException;
import com.example.proyectoRecursosHumanos.model.Employee;
import com.example.proyectoRecursosHumanos.model.Payroll;
import com.example.proyectoRecursosHumanos.model.ReciboPago;
import com.example.proyectoRecursosHumanos.repository.EmployeeRepository;
import com.example.proyectoRecursosHumanos.repository.PayrollRepository;
import com.example.proyectoRecursosHumanos.repository.ReciboPagoRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PayrollService {

    private final PdfGenerationService pdfGenerationService;
    private final PayrollRepository payrollRepository;
    private final EmployeeRepository employeeRepository;
    private final ReciboPagoRepository reciboPagoRepository;
    private final FileStorageService fileStorageService;

    public PayrollService(PayrollRepository payrollRepository, EmployeeRepository employeeRepository, PdfGenerationService pdfGenerationService,
                          ReciboPagoRepository reciboPagoRepository, FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
        this.reciboPagoRepository = reciboPagoRepository;
        this.pdfGenerationService = pdfGenerationService;
        this.employeeRepository = employeeRepository;
        this.payrollRepository = payrollRepository;
        }

    @Transactional
    @PreAuthorize("hasRole('HR')")
    public PayrollResponse crearNomina(PayrollRequest payrollRequest, String userName) {

        BigDecimal salarioBase = payrollRequest.getSalarioBase();
        BigDecimal complementos = payrollRequest.getComplementos();
        BigDecimal deducciones = payrollRequest.getDeducciones();
        BigDecimal impuestos = payrollRequest.getImpuestos();

        BigDecimal totalIngresos = salarioBase.add(complementos);
        BigDecimal totalDeducciones = deducciones.add(impuestos);
        BigDecimal salarioNetoCalculado = totalIngresos.subtract(totalDeducciones);

        Employee employee = employeeRepository.findByUser_UserName(userName)
                .orElseThrow(() -> new ResourceNotFoundException("Empleado", "Username", userName));

        var newPayroll = new Payroll();

        newPayroll.setSalarioBase(salarioBase);
        newPayroll.setComplementos(complementos);
        newPayroll.setDeducciones(deducciones);
        newPayroll.setImpuestos(impuestos);

        newPayroll.setSalarioNeto(salarioNetoCalculado);
        newPayroll.setFechaPago(LocalDate.now());

        newPayroll.setEmpleado(employee);

        var savedPayroll = payrollRepository.save(newPayroll);

        try {
            // 1. Generar el PDF en memoria
            byte[] reciboPdf = pdfGenerationService.generarReciboPagoPdf(savedPayroll);

            // 2. Guardar el archivo PDF en disco y obtener la ruta
            String rutaGuardada = fileStorageService.savePaySlip(
                    savedPayroll.getId(),
                    reciboPdf,
                    savedPayroll.getEmpleado().getNombre()
            );

            // 3. Registrar la ruta en la base de datos
            ReciboPago recibo = new ReciboPago();
            recibo.setPayroll(savedPayroll);
            recibo.setRutaArchivo(rutaGuardada);
            reciboPagoRepository.save(recibo);

        } catch (RuntimeException e) {
            System.err.println("Advertencia: Falló el procesamiento del recibo para el pago ID: " + savedPayroll.getId());
            e.printStackTrace();
        }

        return new PayrollResponse(savedPayroll);
    }

    @Transactional
    @PreAuthorize("hasAnyRole('HR','ADMIN')")
    public List<PayrollResponse> obtenerNominasPorMes (int year, int month) {

        //Define el mes objetivo
        YearMonth targetMonth = YearMonth.of(year, month);

        //Calcula las fechas de inicio y fin del mes
        LocalDate startDate = targetMonth.atDay(1); // Primer día del mes
        LocalDate endDate = targetMonth.atEndOfMonth(); // Último día del mes

        List<Payroll> payrolls = payrollRepository.findByFechaPagoBetween(startDate, endDate);

        // Si no hay pagos devuelve una lista vacía
        if (payrolls.isEmpty()) {
            return List.of();
        }

        //Stream API para mapear cada Payroll a un PayrollResponse
        return payrolls.stream()
                .map(PayrollResponse::new)
                .collect(Collectors.toList());
    }

    @PreAuthorize("isAuthenticated()")
    public byte[] descargarReciboPago(Integer payrollId) {

        // Busca el registro del ReciboPago asociado al ID de la nómina
        ReciboPago reciboPago = reciboPagoRepository.findByPayrollId(payrollId);

        if (reciboPago == null) {
            throw new ResourceNotFoundException("ReciboPago", "Payroll ID", payrollId.toString());
        }

        //Obtiene la ruta del archivo guardada en la base de datos
        String rutaArchivo = reciboPago.getRutaArchivo();
        Path path = Paths.get(rutaArchivo);

        //Lee los bytes del archivo en el sistema de ficheros
        try {
            if (!Files.exists(path)) {
                throw new RuntimeException("El archivo PDF no se encontró en la ruta: " + rutaArchivo);
            }
            return Files.readAllBytes(path);

        } catch (IOException e) {
            throw new RuntimeException("Error al leer el archivo PDF: " + e.getMessage(), e);
        }
    }


}
