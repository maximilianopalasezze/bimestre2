package com.duoc.educloud.dto;

public class ResumenInscripcionMensaje {

    private Long idInscripcion;
    private String nombreEstudiante;
    private String correoEstudiante;
    private Integer totalPagar;
    private String fechaInscripcion;
    private String detalleResumen;

    public ResumenInscripcionMensaje() {
    }

    public ResumenInscripcionMensaje(
            Long idInscripcion,
            String nombreEstudiante,
            String correoEstudiante,
            Integer totalPagar,
            String fechaInscripcion,
            String detalleResumen
    ) {
        this.idInscripcion = idInscripcion;
        this.nombreEstudiante = nombreEstudiante;
        this.correoEstudiante = correoEstudiante;
        this.totalPagar = totalPagar;
        this.fechaInscripcion = fechaInscripcion;
        this.detalleResumen = detalleResumen;
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

    public Integer getTotalPagar() {
        return totalPagar;
    }

    public void setTotalPagar(Integer totalPagar) {
        this.totalPagar = totalPagar;
    }

    public String getFechaInscripcion() {
        return fechaInscripcion;
    }

    public void setFechaInscripcion(String fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }

    public String getDetalleResumen() {
        return detalleResumen;
    }

    public void setDetalleResumen(String detalleResumen) {
        this.detalleResumen = detalleResumen;
    }
}