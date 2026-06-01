package com.duoc.educloud.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public class InscripcionRequest {

    @NotBlank(message = "El nombre del estudiante es obligatorio")
    private String nombreEstudiante;

    @Email(message = "El correo debe tener un formato válido")
    @NotBlank(message = "El correo del estudiante es obligatorio")
    private String correoEstudiante;

    @NotEmpty(message = "Debe seleccionar al menos un curso")
    private List<Long> idsCursos;

    public String getNombreEstudiante() {
        return nombreEstudiante;
    }

    public void setNombreEstudiante(String nombreEstudiante) {
        this.nombreEstudiante = nombreEstudiante;
    }

    public String getCorreoEstudiante() {
        return correoEstudiante;
    }

    public void setCorreoEstudiante(String correoEstudiante) {
        this.correoEstudiante = correoEstudiante;
    }

    public List<Long> getIdsCursos() {
        return idsCursos;
    }

    public void setIdsCursos(List<Long> idsCursos) {
        this.idsCursos = idsCursos;
    }
}
