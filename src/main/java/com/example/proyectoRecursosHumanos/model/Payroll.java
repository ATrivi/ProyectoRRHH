package com.example.proyectoRecursosHumanos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "nominas")
public class Payroll {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;
    @NotNull(message = "El salario base es obligatorio")
    private BigDecimal salarioBase;
    @NotNull(message = "Los complementos son obligatorias")
    private BigDecimal complementos;
    @NotNull(message = "Las deducciones son obligatorias")
    private BigDecimal deducciones;
    @NotNull(message = "Los impuestos son obligatorios")
    private BigDecimal impuestos;
    private LocalDate fechaPago;
    @Enumerated(EnumType.STRING)
    private CicloPago cicloPago;
    private BigDecimal salarioNeto;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "empleado_id", nullable = false)
    private Employee empleado;

    public Payroll() {
    }

    public Payroll(Integer id, BigDecimal salarioBase, BigDecimal complementos, BigDecimal deducciones, BigDecimal impuestos, LocalDate fechaPago, CicloPago cicloPago, BigDecimal salarioNeto, Employee empleado) {
        this.id = id;
        this.salarioBase = salarioBase;
        this.complementos = complementos;
        this.deducciones = deducciones;
        this.impuestos = impuestos;
        this.fechaPago = fechaPago;
        this.cicloPago = cicloPago;
        this.salarioNeto = salarioNeto;
        this.empleado = empleado;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getSalarioBase() {
        return salarioBase;
    }

    public void setSalarioBase(BigDecimal salarioBase) {
        this.salarioBase = salarioBase;
    }

    public BigDecimal getDeducciones() {
        return deducciones;
    }

    public void setDeducciones(BigDecimal deducciones) {
        this.deducciones = deducciones;
    }

    public BigDecimal getComplementos() {
        return complementos;
    }

    public void setComplementos(BigDecimal complementos) {
        this.complementos = complementos;
    }

    public CicloPago getCicloPago() {
        return cicloPago;
    }

    public void setCicloPago(CicloPago cicloPago) {
        this.cicloPago = cicloPago;
    }

    public BigDecimal getSalarioNeto() {
        return salarioNeto;
    }

    public void setSalarioNeto(BigDecimal salarioNeto) {
        this.salarioNeto = salarioNeto;
    }

    public LocalDate getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(LocalDate fechaPago) {
        this.fechaPago = fechaPago;
    }

    public BigDecimal getImpuestos() {
        return impuestos;
    }

    public void setImpuestos(BigDecimal impuestos) {
        this.impuestos = impuestos;
    }

    public Employee getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Employee empleado) {
        this.empleado = empleado;
    }
}
