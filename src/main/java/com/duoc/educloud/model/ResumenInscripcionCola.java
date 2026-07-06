package com.duoc.educloud.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "resumenes_inscripcion_mq")
public class ResumenInscripcionCola {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_inscripcion", nullable = false)
    private Long idInscripcion;

    @Column(name = "nombre_estudiante", nullable = false, length = 100)
    private String nombreEstudiante;

    @Column(name = "correo_estudiante", nullable = false, length = 100)
    private String correoEstudiante;

    @Column(name = "total_pagar", nullable = false)
    private Integer totalPagar;

    @Column(name = "fecha_inscripcion", nullable = false, length = 50)
    private String fechaInscripcion;

    @Lob
    @Column(name = "detalle_resumen", nullable = false)
    private String detalleResumen;

    @Column(name = "fecha_consumo", nullable = false)
    private LocalDateTime fechaConsumo;

    public ResumenInscripcionCola() {
    }

    public ResumenInscripcionCola(
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
        this.fechaConsumo = LocalDateTime.now();
    }

    public Long getId() {
        return id;
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

    public LocalDateTime getFechaConsumo() {
        return fechaConsumo;
    }

    public void setFechaConsumo(LocalDateTime fechaConsumo) {
        this.fechaConsumo = fechaConsumo;
    }
}