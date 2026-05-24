package com.duoc.educloud.service;

import com.duoc.educloud.dto.CursoResumenDTO;
import com.duoc.educloud.dto.InscripcionRequest;
import com.duoc.educloud.dto.InscripcionResponse;
import com.duoc.educloud.exception.SolicitudInvalidaException;
import com.duoc.educloud.model.Curso;
import com.duoc.educloud.model.Inscripcion;
import com.duoc.educloud.repository.InscripcionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class InscripcionService {

    private final CursoService cursoService;
    private final InscripcionRepository inscripcionRepository;

    public InscripcionService(CursoService cursoService, InscripcionRepository inscripcionRepository) {
        this.cursoService = cursoService;
        this.inscripcionRepository = inscripcionRepository;
    }

    @Transactional
    public InscripcionResponse inscribirEstudiante(InscripcionRequest request) {
        validarCursosRepetidos(request.getIdsCursos());

        List<Curso> cursosSeleccionados = new ArrayList<>();
        int total = 0;

        for (Long idCurso : request.getIdsCursos()) {
            Curso curso = cursoService.buscarPorId(idCurso);
            cursosSeleccionados.add(curso);
            total += curso.getCosto();
        }

        Inscripcion inscripcion = new Inscripcion(
                request.getNombreEstudiante(),
                request.getCorreoEstudiante(),
                total
        );

        for (Curso curso : cursosSeleccionados) {
            inscripcion.agregarCurso(curso);
        }

        Inscripcion inscripcionGuardada = inscripcionRepository.save(inscripcion);

        List<CursoResumenDTO> resumenCursos = cursosSeleccionados.stream()
                .map(curso -> new CursoResumenDTO(
                        curso.getId(),
                        curso.getNombre(),
                        curso.getInstructor(),
                        curso.getDuracion(),
                        curso.getCosto()
                ))
                .toList();

        return new InscripcionResponse(
                inscripcionGuardada.getId(),
                inscripcionGuardada.getNombreEstudiante(),
                inscripcionGuardada.getCorreoEstudiante(),
                resumenCursos,
                inscripcionGuardada.getTotalPagar(),
                inscripcionGuardada.getFecha()
        );
    }

    private void validarCursosRepetidos(List<Long> idsCursos) {
        Set<Long> idsSinRepetir = new HashSet<>(idsCursos);

        if (idsSinRepetir.size() != idsCursos.size()) {
            throw new SolicitudInvalidaException("No se puede inscribir dos veces el mismo curso en una sola inscripción");
        }
    }
}
