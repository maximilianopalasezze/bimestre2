package com.duoc.educloud.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "inscripciones")
public class Inscripcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del estudiante es obligatorio")
    @Column(name = "nombre_estudiante", nullable = false, length = 100)
    private String nombreEstudiante;

    @Email(message = "El correo debe tener un formato válido")
    @NotBlank(message = "El correo del estudiante es obligatorio")
    @Column(name = "correo_estudiante", nullable = false, length = 100)
    private String correoEstudiante;

    @Column(name = "total_pagar", nullable = false)
    private Integer totalPagar;

    @Column(nullable = false)
    private LocalDateTime fecha;

    @OneToMany(mappedBy = "inscripcion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InscripcionCurso> cursosInscritos = new ArrayList<>();

    public Inscripcion() {
        this.fecha = LocalDateTime.now();
    }

    public Inscripcion(String nombreEstudiante, String correoEstudiante, Integer totalPagar) {
        this.nombreEstudiante = nombreEstudiante;
        this.correoEstudiante = correoEstudiante;
        this.totalPagar = totalPagar;
        this.fecha = LocalDateTime.now();
    }

    public void agregarCurso(Curso curso) {
        InscripcionCurso detalle = new InscripcionCurso(this, curso, curso.getCosto());
        cursosInscritos.add(detalle);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public List<InscripcionCurso> getCursosInscritos() {
        return cursosInscritos;
    }

    public void setCursosInscritos(List<InscripcionCurso> cursosInscritos) {
        this.cursosInscritos = cursosInscritos;
    }
}
