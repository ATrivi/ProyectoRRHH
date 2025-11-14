package com.example.proyectoRecursosHumanos.dto;

import com.example.proyectoRecursosHumanos.model.TipoAusencia;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class AbsenceRequest {

    @NotNull(message = "El tipo de ausencia es obligatorio")
    private TipoAusencia tipoAusencia;

    @NotNull(message = "Los días de ausencia son obligatorios")
    @Positive(message = "Los días de ausencia deben ser un número positivo")
    private Integer diasAusencia;

    public AbsenceRequest() {
    }

    public AbsenceRequest(TipoAusencia tipoAusencia, Integer diasAusencia) {
        this.tipoAusencia = tipoAusencia;
        this.diasAusencia = diasAusencia;
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
}