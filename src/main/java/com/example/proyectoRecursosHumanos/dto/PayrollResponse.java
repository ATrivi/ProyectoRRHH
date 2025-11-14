package com.example.proyectoRecursosHumanos.dto;

import com.example.proyectoRecursosHumanos.model.Payroll;

import java.math.BigDecimal;


public class PayrollResponse {

    private String userName;
    private BigDecimal salarioBase;
    private BigDecimal complementos;
    private BigDecimal deducciones;
    private BigDecimal impuestos;
    private BigDecimal salarioNeto;

    public PayrollResponse(String userName, BigDecimal salarioBase, BigDecimal complementos, BigDecimal deducciones, BigDecimal impuestos, BigDecimal salarioNeto) {
        this.userName = userName;
        this.salarioBase = salarioBase;
        this.complementos = complementos;
        this.deducciones = deducciones;
        this.impuestos = impuestos;
        this.salarioNeto = salarioNeto;
    }

    public PayrollResponse(Payroll payroll) {

        this.userName = payroll.getEmpleado().getUser().getUserName();
        this.salarioBase = payroll.getSalarioBase();
        this.complementos = payroll.getComplementos();
        this.deducciones = payroll.getDeducciones();
        this.impuestos = payroll.getImpuestos();
        this.salarioNeto = payroll.getSalarioNeto();
    }

    public PayrollResponse() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public BigDecimal getImpuestos() {
        return impuestos;
    }

    public void setImpuestos(BigDecimal impuestos) {
        this.impuestos = impuestos;
    }

    public BigDecimal getSalarioNeto() {
        return salarioNeto;
    }

    public void setSalarioNeto(BigDecimal salarioNeto) {
        this.salarioNeto = salarioNeto;
    }
}
