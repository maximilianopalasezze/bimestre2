package com.duoc.educloud.dto;

import java.time.LocalDateTime;
import java.util.List;

public class InscripcionResponse {

    private Long idInscripcion;
    private String nombreEstudiante;
    private String correoEstudiante;
    private List<CursoResumenDTO> cursosSeleccionados;
    private Integer totalPagar;
    private LocalDateTime fecha;

    public InscripcionResponse() {
    }

    public InscripcionResponse(Long idInscripcion, String nombreEstudiante, String correoEstudiante,
                               List<CursoResumenDTO> cursosSeleccionados, Integer totalPagar, LocalDateTime fecha) {
        this.idInscripcion = idInscripcion;
        this.nombreEstudiante = nombreEstudiante;
        this.correoEstudiante = correoEstudiante;
        this.cursosSeleccionados = cursosSeleccionados;
        this.totalPagar = totalPagar;
        this.fecha = fecha;
    }

    public Long getIdInscripcion() {
        return idInscripcion;
    }

    public void setIdInscripcion(Long idInscripcion) {
        this.idInscripcion = idInscripcion;
    }

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

    public List<CursoResumenDTO> getCursosSeleccionados() {
        return cursosSeleccionados;
    }

    public void setCursosSeleccionados(List<CursoResumenDTO> cursosSeleccionados) {
        this.cursosSeleccionados = cursosSeleccionados;
    }

    public Integer getTotalPagar() {
        return totalPagar;
    }

    public void setTotalPagar(Integer totalPagar) {
        this.totalPagar = totalPagar;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}
