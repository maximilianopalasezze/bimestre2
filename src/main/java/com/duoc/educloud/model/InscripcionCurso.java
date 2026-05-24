package com.duoc.educloud.model;

import jakarta.persistence.*;

@Entity
@Table(name = "inscripcion_cursos")
public class InscripcionCurso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inscripcion_id", nullable = false)
    private Inscripcion inscripcion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id", nullable = false)
    private Curso curso;

    @Column(name = "costo_curso", nullable = false)
    private Integer costoCurso;

    public InscripcionCurso() {
    }

    public InscripcionCurso(Inscripcion inscripcion, Curso curso, Integer costoCurso) {
        this.inscripcion = inscripcion;
        this.curso = curso;
        this.costoCurso = costoCurso;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Inscripcion getInscripcion() {
        return inscripcion;
    }

    public void setInscripcion(Inscripcion inscripcion) {
        this.inscripcion = inscripcion;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Integer getCostoCurso() {
        return costoCurso;
    }

    public void setCostoCurso(Integer costoCurso) {
        this.costoCurso = costoCurso;
    }
}
