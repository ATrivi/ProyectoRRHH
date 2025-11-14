package com.example.proyectoRecursosHumanos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table (name = "ausencias")
public class Absence {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "El tipo de ausencia es obligatoria")
    private TipoAusencia tipoAusencia;

    @NotNull(message = "Los días de ausencia son obligatorios")
    private Integer diasAusencia;

    @Enumerated(EnumType.STRING)
    private EstadoAusencia estado;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "empleado_id", nullable = false)
    private Employee empleado;

    public Absence(Integer id, TipoAusencia tipoAusencia, Integer diasAusencia, EstadoAusencia estado, Employee empleado) {
        this.id = id;
        this.tipoAusencia = tipoAusencia;
        this.diasAusencia = diasAusencia;
        this.estado = estado;
        this.empleado = empleado;
    }
    public Absence() {
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

    public Employee getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Employee empleado) {
        this.empleado = empleado;
    }
}