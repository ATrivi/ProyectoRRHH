package com.example.proyectoRecursosHumanos.dto;

import com.example.proyectoRecursosHumanos.model.Employee;

public class EmployeeList {

    private Integer id;
    private String nombre;
    private String apellidos;
    private String puesto;

    public EmployeeList(Employee empleado) {
        this.id = empleado.getId();
        this.nombre = empleado.getNombre();
        this.apellidos = empleado.getApellidos();

        // Si el empleado tiene un JobPosition asignado, toma su nombrePuesto
        if (empleado.getPuesto() != null) {
            this.puesto = empleado.getPuesto().getNombrePuesto();
        } else {
            this.puesto = null;
        }
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
}
