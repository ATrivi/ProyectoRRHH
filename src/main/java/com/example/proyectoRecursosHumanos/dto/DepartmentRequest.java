package com.example.proyectoRecursosHumanos.dto;

import com.example.proyectoRecursosHumanos.model.Employee;
import jakarta.validation.constraints.NotBlank;

public class DepartmentRequest {
    @NotBlank(message = "El nombre del departamento es obligatorio")
    private String nombreDepartamento;
    private Integer responsableId;

    public DepartmentRequest() {
    }

    public DepartmentRequest(String nombreDepartamento, Integer responsableId) {
        this.nombreDepartamento = nombreDepartamento;
        this.responsableId = responsableId;
    }

    public String getNombreDepartamento() {
        return nombreDepartamento;
    }

    public void setNombreDepartamento(String nombreDepartamento) {
        this.nombreDepartamento = nombreDepartamento;
    }

    public Integer getResponsableId() {
        return responsableId;
    }

    public void setResponsableId(Integer responsableId) {
        this.responsableId = responsableId;
    }
}

