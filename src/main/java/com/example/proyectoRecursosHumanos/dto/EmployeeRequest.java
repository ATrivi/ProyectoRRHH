package com.example.proyectoRecursosHumanos.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class EmployeeRequest {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;
    @NotBlank(message = "El apellido es obligatorio")
    private String apellidos;
    @NotNull(message = "El puesto es obligatorio")
    private Integer jobPositionId; //no necesito enviar el objeto entero sino solo sus ID
    @NotNull(message = "El departamento es obligatorio")
    private Integer departmentId;
    @NotNull
    private Boolean estado;
    @NotNull  @DecimalMin("0.0")
    private BigDecimal salario;

    public EmployeeRequest() {}

    public EmployeeRequest(String nombre, Boolean estado, Integer departmentId, Integer jobPositionId, String apellidos, BigDecimal salario) {
        this.nombre = nombre;
        this.estado = estado;
        this.departmentId = departmentId;
        this.jobPositionId = jobPositionId;
        this.apellidos = apellidos;
        this.salario = salario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public Integer getJobPositionId() {
        return jobPositionId;
    }

    public void setJobPositionId(Integer jobPositionId) {
        this.jobPositionId = jobPositionId;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public BigDecimal getSalario() {return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }
}
