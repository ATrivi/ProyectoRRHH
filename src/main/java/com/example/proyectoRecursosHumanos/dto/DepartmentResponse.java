package com.example.proyectoRecursosHumanos.dto;

public class DepartmentResponse {

    private Integer id;
    private String nombreDepartamento;
    private String nombreResponsable;

    public DepartmentResponse(Integer id, String nombreDepartamento, String nombreResponsable) {
        this.id = id;
        this.nombreDepartamento = nombreDepartamento;
        this.nombreResponsable = nombreResponsable;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombreDepartamento() {
        return nombreDepartamento;
    }

    public void setNombreDepartamento(String nombreDepartamento) {
        this.nombreDepartamento = nombreDepartamento;
    }

    public String getNombreResponsable() {
        return nombreResponsable;
    }

    public void setNombreResponsable(String nombreResponsable) {
        this.nombreResponsable = nombreResponsable;
    }
}
