package com.example.proyectoRecursosHumanos.dto;

import com.example.proyectoRecursosHumanos.model.Employee;

public class EmployeeResponse {

    private Integer id;
    private String nombre;
    private String apellidos;
    private String puesto;
    private String departamento;
    private String estado;

    public EmployeeResponse() {
    }

    public EmployeeResponse(Integer id, String nombre, String apellidos, String puesto, String departamento, String estado) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.puesto = puesto;
        this.departamento = departamento;
        this.estado = estado;
    }

    public EmployeeResponse(Employee empleado) {
        this.id = empleado.getId();
        this.nombre = empleado.getNombre();
        this.apellidos = empleado.getApellidos();
        this.puesto = empleado.getPuesto().getNombrePuesto();
        this.departamento = empleado.getDepartamento().getNombreDepartamento();
        this.estado = empleado.getEstado() ? "Activo" : "Inactivo";
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
