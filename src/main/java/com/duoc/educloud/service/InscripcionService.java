package com.duoc.educloud.service;

import com.duoc.educloud.dto.CursoResumenDTO;
import com.duoc.educloud.dto.InscripcionRequest;
import com.duoc.educloud.dto.InscripcionResponse;
import com.duoc.educloud.dto.ResumenInscripcionMensaje;
import com.duoc.educloud.exception.RecursoNoEncontradoException;
import com.duoc.educloud.exception.SolicitudInvalidaException;
import com.duoc.educloud.model.Curso;
import com.duoc.educloud.model.Inscripcion;
import com.duoc.educloud.repository.InscripcionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class InscripcionService {

    private final CursoService cursoService;
    private final InscripcionRepository inscripcionRepository;
    private final ResumenInscripcionProducerService resumenInscripcionProducerService;

    public InscripcionService(
            CursoService cursoService,
            InscripcionRepository inscripcionRepository,
            ResumenInscripcionProducerService resumenInscripcionProducerService
    ) {
        this.cursoService = cursoService;
        this.inscripcionRepository = inscripcionRepository;
        this.resumenInscripcionProducerService = resumenInscripcionProducerService;
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

    @Transactional(readOnly = true)
    public Path generarArchivoResumenLocal(Long idInscripcion) throws IOException {
        Inscripcion inscripcion = inscripcionRepository.findById(idInscripcion)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No existe una inscripción con el ID " + idInscripcion
                ));

        Files.createDirectories(Path.of("resumenes"));

        Path rutaArchivo = Path.of(
                "resumenes",
                "resumen-inscripcion-" + idInscripcion + ".txt"
        );

        String contenidoResumen = construirContenidoResumen(inscripcion);

        Files.writeString(rutaArchivo, contenidoResumen);

        ResumenInscripcionMensaje mensaje = new ResumenInscripcionMensaje(
                inscripcion.getId(),
                inscripcion.getNombreEstudiante(),
                inscripcion.getCorreoEstudiante(),
                inscripcion.getTotalPagar(),
                inscripcion.getFecha().toString(),
                contenidoResumen
        );

        resumenInscripcionProducerService.enviarResumen(mensaje);

        return rutaArchivo;
    }

    public Path obtenerRutaArchivoResumen(Long idInscripcion) {
        return Path.of("resumenes", "resumen-inscripcion-" + idInscripcion + ".txt");
    }

    private String construirContenidoResumen(Inscripcion inscripcion) {
        StringBuilder contenido = new StringBuilder();

        contenido.append("RESUMEN DE INSCRIPCIÓN\n");
        contenido.append("========================\n\n");
        contenido.append("Número de resumen: ").append(inscripcion.getId()).append("\n");
        contenido.append("Nombre estudiante: ").append(inscripcion.getNombreEstudiante()).append("\n");
        contenido.append("Correo estudiante: ").append(inscripcion.getCorreoEstudiante()).append("\n");
        contenido.append("Fecha de inscripción: ").append(inscripcion.getFecha()).append("\n\n");
        contenido.append("Cursos inscritos:\n");

        for (var detalle : inscripcion.getCursosInscritos()) {
            contenido.append("- ")
                    .append(detalle.getCurso().getNombre())
                    .append(" | Instructor: ")
                    .append(detalle.getCurso().getInstructor())
                    .append(" | Duración: ")
                    .append(detalle.getCurso().getDuracion())
                    .append(" | Costo: $")
                    .append(detalle.getCostoCurso())
                    .append("\n");
        }

        contenido.append("\nTotal a pagar: $")
                .append(inscripcion.getTotalPagar())
                .append("\n");

        contenido.append("\nArchivo generado físicamente desde el microservicio EduCloud Inscripciones.\n");

        return contenido.toString();
    }

    private void validarCursosRepetidos(List<Long> idsCursos) {
        Set<Long> idsSinRepetir = new HashSet<>(idsCursos);

        if (idsSinRepetir.size() != idsCursos.size()) {
            throw new SolicitudInvalidaException(
                    "No se puede inscribir dos veces el mismo curso en una sola inscripción"
            );
        }
    }
}