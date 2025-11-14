package com.example.proyectoRecursosHumanos.dto;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;


public class PayrollRequest {

    @NotNull(message = "El salario base es obligatorio")
    private BigDecimal salarioBase;
    @NotNull(message = "Los complementos son obligatorias")
    private BigDecimal complementos;
    @NotNull(message = "Las deducciones son obligatorias")
    private BigDecimal deducciones;
    @NotNull(message = "Los impuestos son obligatorios")
    private BigDecimal impuestos;
    private BigDecimal salarioNeto;

    public PayrollRequest() {
    }

    public PayrollRequest(BigDecimal salarioBase, BigDecimal complementos, BigDecimal deducciones, BigDecimal impuestos, BigDecimal salarioNeto) {
        this.salarioBase = salarioBase;
        this.complementos = complementos;
        this.deducciones = deducciones;
        this.impuestos = impuestos;
        this.salarioNeto = salarioNeto;
    }

    public BigDecimal getSalarioBase() {
        return salarioBase;
    }

    public void setSalarioBase(BigDecimal salarioBase) {
        this.salarioBase = salarioBase;
    }

    public BigDecimal getComplementos() {
        return complementos;
    }

    public void setComplementos(BigDecimal complementos) {
        this.complementos = complementos;
    }

    public BigDecimal getDeducciones() {
        return deducciones;
    }

    public void setDeducciones(BigDecimal deducciones) {
        this.deducciones = deducciones;
    }

    public BigDecimal getSalarioNeto() {
        return salarioNeto;
    }

    public void setSalarioNeto(BigDecimal salarioNeto) {
        this.salarioNeto = salarioNeto;
    }

    public BigDecimal getImpuestos() {
        return impuestos;
    }

    public void setImpuestos(BigDecimal impuestos) {
        this.impuestos = impuestos;
    }
}
