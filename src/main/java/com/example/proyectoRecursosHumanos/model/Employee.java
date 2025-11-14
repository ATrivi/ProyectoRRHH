package com.example.proyectoRecursosHumanos.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

@Entity
@Table(name = "employees")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = true)
    private User user;
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;
    @NotBlank(message = "Los apellidos son obligatorios")
    private String apellidos;
    private Integer diasAusenciaTotales = 0;
    @PositiveOrZero
    @NotNull
    private BigDecimal salario;
    @ManyToOne(fetch = FetchType.LAZY, optional = false) //Solo carga el JobPosition si realmente lo usa en el código
    @JoinColumn(name = "job_position_id", nullable = false)
    private JobPosition puesto;

    @ManyToOne(fetch = FetchType.LAZY, optional = false) //Optional false indica que que la relación es obligatoria y no puede guardar un empleado sin departamento
    @JoinColumn(name = "department_id", nullable = false)
    @JsonBackReference
    private Department departamento;
    private Boolean estado;

    public Employee() {
    }

    public Employee(Integer id, User user, String nombre, String apellidos, Integer diasAusenciaTotales, BigDecimal salario, JobPosition puesto, Department departamento, Boolean estado) {
        this.id = id;
        this.user = user;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.diasAusenciaTotales = diasAusenciaTotales;
        this.salario = salario;
        this.puesto = puesto;
        this.departamento = departamento;
        this.estado = estado;
    }

    public Integer getDiasAusenciaTotales() {
        return diasAusenciaTotales;
    }

    public void setDiasAusenciaTotales(Integer diasAusenciaTotales) {
        this.diasAusenciaTotales = diasAusenciaTotales;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public JobPosition getPuesto() {
        return puesto;
    }

    public void setPuesto(JobPosition puesto) {
        this.puesto = puesto;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }

    public Department getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Department departamento) {
        this.departamento = departamento;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
}
