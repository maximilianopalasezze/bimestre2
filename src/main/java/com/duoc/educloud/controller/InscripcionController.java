package com.duoc.educloud.controller;

import com.duoc.educloud.dto.InscripcionRequest;
import com.duoc.educloud.dto.InscripcionResponse;
import com.duoc.educloud.dto.ModificarResumenRequest;
import com.duoc.educloud.service.InscripcionService;
import com.duoc.educloud.service.S3ResumenService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

@RestController
@RequestMapping("/api/inscripciones")
public class InscripcionController {

    private final InscripcionService inscripcionService;
    private final S3ResumenService s3ResumenService;

    public InscripcionController(InscripcionService inscripcionService, S3ResumenService s3ResumenService) {
        this.inscripcionService = inscripcionService;
        this.s3ResumenService = s3ResumenService;
    }

    @PostMapping
    public ResponseEntity<InscripcionResponse> inscribirEstudiante(@Valid @RequestBody InscripcionRequest request) {
        InscripcionResponse response = inscripcionService.inscribirEstudiante(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/{idInscripcion}/resumen/generar")
    public ResponseEntity<?> generarResumenLocal(@PathVariable Long idInscripcion) {
        try {
            Path rutaArchivo = inscripcionService.generarArchivoResumenLocal(idInscripcion);

            return ResponseEntity.ok(Map.of(
                    "mensaje", "Resumen generado correctamente en el computador",
                    "numeroResumen", idInscripcion,
                    "archivoLocal", rutaArchivo.toString()
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                    "error", "No se pudo generar el resumen local",
                    "detalle", e.getMessage()
            ));
        }
    }

    @PostMapping("/{idInscripcion}/resumen/subir")
    public ResponseEntity<?> subirResumenS3(@PathVariable Long idInscripcion) {
        try {
            Path rutaArchivo = inscripcionService.obtenerRutaArchivoResumen(idInscripcion);

            if (!Files.exists(rutaArchivo)) {
                rutaArchivo = inscripcionService.generarArchivoResumenLocal(idInscripcion);
            }

            String key = s3ResumenService.subirResumen(idInscripcion, rutaArchivo);

            return ResponseEntity.ok(Map.of(
                    "mensaje", "Resumen subido correctamente al bucket de AWS S3",
                    "numeroResumen", idInscripcion,
                    "bucket", "educloud-inscripciones-2026",
                    "carpeta", String.valueOf(idInscripcion),
                    "ubicacionS3", key
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                    "error", "No se pudo subir el resumen a AWS S3",
                    "detalle", e.getMessage()
            ));
        }
    }

    @PutMapping("/{idInscripcion}/resumen")
    public ResponseEntity<?> modificarResumenS3(@PathVariable Long idInscripcion,
                                                @Valid @RequestBody ModificarResumenRequest request) {
        try {
            String key = s3ResumenService.modificarResumen(idInscripcion, request.getContenido());

            return ResponseEntity.ok(Map.of(
                    "mensaje", "Resumen modificado correctamente en AWS S3",
                    "numeroResumen", idInscripcion,
                    "ubicacionS3", key
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                    "error", "No se pudo modificar el resumen en AWS S3",
                    "detalle", e.getMessage()
            ));
        }
    }

    @GetMapping("/{idInscripcion}/resumen/descargar")
    public ResponseEntity<byte[]> descargarResumenS3(@PathVariable Long idInscripcion) {
        try {
            byte[] archivo = s3ResumenService.descargarResumen(idInscripcion);
            String nombreArchivo = s3ResumenService.crearNombreArchivo(idInscripcion);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + nombreArchivo)
                    .contentType(MediaType.TEXT_PLAIN)
                    .body(archivo);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .contentType(MediaType.TEXT_PLAIN)
                    .body(("No se pudo descargar el resumen: " + e.getMessage()).getBytes());
        }
    }

    @DeleteMapping("/{idInscripcion}/resumen")
    public ResponseEntity<?> borrarResumenS3(@PathVariable Long idInscripcion) {
        try {
            s3ResumenService.borrarResumen(idInscripcion);

            return ResponseEntity.ok(Map.of(
                    "mensaje", "Resumen eliminado correctamente del bucket de AWS S3",
                    "numeroResumen", idInscripcion
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                    "error", "No se pudo borrar el resumen de AWS S3",
                    "detalle", e.getMessage()
            ));
        }
    }
}
