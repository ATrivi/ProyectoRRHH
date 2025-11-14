package com.example.proyectoRecursosHumanos.dto;

import com.example.proyectoRecursosHumanos.model.EstadoAusencia;
import com.example.proyectoRecursosHumanos.model.TipoAusencia;
import com.example.proyectoRecursosHumanos.model.Absence;


public class AbsenceResponse {

    private Integer id;
    private TipoAusencia tipoAusencia;
    private Integer diasAusencia;
    private EstadoAusencia estado;
    private Integer empleadoId;
    private String empleadoNombreCompleto;

    public AbsenceResponse(Absence absence) {
        this.id = absence.getId();
        this.tipoAusencia = absence.getTipoAusencia();
        this.diasAusencia = absence.getDiasAusencia();
        this.estado = absence.getEstado();
        this.empleadoId = absence.getEmpleado().getId();
        this.empleadoNombreCompleto = absence.getEmpleado().getUser().getUserName();
        }

    public AbsenceResponse() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TipoAusencia getTipoAusencia() {
        return tipoAusencia;
    }

    public void setTipoAusencia(TipoAusencia tipoAusencia) {
        this.tipoAusencia = tipoAusencia;
    }

    public Integer getDiasAusencia() {
        return diasAusencia;
    }

    public void setDiasAusencia(Integer diasAusencia) {
        this.diasAusencia = diasAusencia;
    }

    public EstadoAusencia getEstado() {
        return estado;
    }

    public void setEstado(EstadoAusencia estado) {
        this.estado = estado;
    }

    public Integer getEmpleadoId() {
        return empleadoId;
    }

    public void setEmpleadoId(Integer empleadoId) {
        this.empleadoId = empleadoId;
    }

    public String getEmpleadoNombreCompleto() {
        return empleadoNombreCompleto;
    }

    public void setEmpleadoNombreCompleto(String empleadoNombreCompleto) {
        this.empleadoNombreCompleto = empleadoNombreCompleto;
    }
}
