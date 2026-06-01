package com.duoc.educloud.dto;

import jakarta.validation.constraints.NotBlank;

public class ModificarResumenRequest {

    @NotBlank(message = "El contenido del resumen no puede estar vacío")
    private String contenido;

    public ModificarResumenRequest() {
    }

    public ModificarResumenRequest(String contenido) {
        this.contenido = contenido;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
}
