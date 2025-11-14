package com.example.proyectoRecursosHumanos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table (name = "job_Positions")
public class JobPosition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank(message = "El nombre del puesto es obligatorio")
    @Column(name = "nombre_puesto", unique = true, nullable = false)
    private String nombrePuesto;

    public JobPosition() {}

    public JobPosition(Integer id, String nombre) {
        this.id = id;
        this.nombrePuesto = nombre;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombrePuesto() {
        return nombrePuesto;
    }

    public void setNombrePuesto(String nombre) {
        this.nombrePuesto = nombre;
    }
}
