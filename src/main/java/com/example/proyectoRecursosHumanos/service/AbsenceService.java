package com.example.proyectoRecursosHumanos.service;

import com.example.proyectoRecursosHumanos.dto.AbsenceRequest;
import com.example.proyectoRecursosHumanos.dto.AbsenceResponse;
import com.example.proyectoRecursosHumanos.exception.ResourceNotFoundException;
import com.example.proyectoRecursosHumanos.model.Absence;
import com.example.proyectoRecursosHumanos.model.Employee;
import com.example.proyectoRecursosHumanos.model.EstadoAusencia;
import com.example.proyectoRecursosHumanos.repository.AbsenceRepository;
import com.example.proyectoRecursosHumanos.repository.EmployeeRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AbsenceService {

    private final AbsenceRepository absenceRepository;
    private final EmployeeRepository employeeRepository;
    private final NotificationService notificationService;

    public AbsenceService(AbsenceRepository absenceRepository, EmployeeRepository employeeRepository, NotificationService notificationService) {
        this.notificationService = notificationService;
        this.absenceRepository = absenceRepository;
        this.employeeRepository = employeeRepository;
    }

    @Transactional
    @PreAuthorize("isAuthenticated()")
    public AbsenceResponse registrarAusencia(AbsenceRequest absenceRequest, String userName) {

        // Mapeo Manual: Request DTO -> Entidad Absence
        Absence newAbsence = new Absence();
        newAbsence.setTipoAusencia(absenceRequest.getTipoAusencia());
        newAbsence.setDiasAusencia(absenceRequest.getDiasAusencia());

        //Buscar y asignar el Empleado
            Employee employee = employeeRepository.findByUser_UserName(userName)
                    .orElseThrow(() -> new ResourceNotFoundException("Empleado", "Username", userName));

            newAbsence.setEmpleado(employee);

            newAbsence.setEstado(EstadoAusencia.PENDIENTE);

        //Guardar la ENTIDAD
        Absence savedAbsence = absenceRepository.save(newAbsence);

        //Mapeo Manual: Entidad Absence -> Response DTO
        // Usamos el constructor del DTO para hacer la conversión
        return new AbsenceResponse(savedAbsence);
        }

    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public AbsenceResponse actualizarEstado(Integer id, EstadoAusencia nuevoEstado) {
        Absence abs = absenceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ausencia", "id", id.toString()));

        if (abs.getEstado().equals(EstadoAusencia.APROBADA) || abs.getEstado().equals(EstadoAusencia.RECHAZADA)) {

            throw new IllegalStateException("La ausencia ID " + id + " ya fue procesada.");
        }

        if (nuevoEstado.equals(EstadoAusencia.APROBADA)) {
            Employee empleado = abs.getEmpleado();
            Integer diasAusencia = abs.getDiasAusencia();

            Integer diasActuales = empleado.getDiasAusenciaTotales();
            if (diasActuales == null) {
                diasActuales = 0;
            }

            Integer totalActualizado = diasActuales + diasAusencia;

            empleado.setDiasAusenciaTotales(totalActualizado);

            employeeRepository.save(empleado);

            notificationService.crearNotificacion(empleado,
                    "Su ausencia de" + abs.getDiasAusencia() + " días ha sido aprobada.", "APROBACIÓN_AUSENCIA");

        }

        abs.setEstado(nuevoEstado);
        Absence savedAbsence = absenceRepository.save(abs);

        return new AbsenceResponse(savedAbsence);
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public List<AbsenceResponse> obtenerAusenciasPorEmpleado(Integer employeeId) {
        Employee empleado = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Empleado", "ID", employeeId.toString()));

        List<Absence> listAbsence = absenceRepository.findByEmpleado(empleado);

        List<AbsenceResponse> listaRespuesta = listAbsence.stream()
                .map(AbsenceResponse::new)
                .collect(Collectors.toList());

        return listaRespuesta;
    }


}


