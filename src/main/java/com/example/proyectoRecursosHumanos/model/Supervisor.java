package com.example.proyectoRecursosHumanos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
@Table (name = "Supervisors")
public class Supervisor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "subordinado_id")
    private Employee subordinado;
    @ManyToOne
    @JoinColumn(name = "supervisor_id")
    private Employee supervisor;
    @NotNull(message = "La fecha es obligatoria")
    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    public Supervisor() {}

    public Supervisor(int id, Employee subordinado, Employee supervisor, LocalDate fechaInicio, LocalDate fechaFin) {
        this.id = id;
        this.subordinado = subordinado;
        this.supervisor = supervisor;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Employee getSubordinado() {
        return subordinado;
    }

    public void setSubordinado(Employee subordinado) {
        this.subordinado = subordinado;
    }

    public Employee getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Employee supervisor) {
        this.supervisor = supervisor;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }
}
