package com.duoc.educloud.controller;

import com.duoc.educloud.dto.InscripcionRequest;
import com.duoc.educloud.dto.InscripcionResponse;
import com.duoc.educloud.service.InscripcionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inscripciones")
public class InscripcionController {

    private final InscripcionService inscripcionService;

    public InscripcionController(InscripcionService inscripcionService) {
        this.inscripcionService = inscripcionService;
    }

    @PostMapping
    public ResponseEntity<InscripcionResponse> inscribirEstudiante(@Valid @RequestBody InscripcionRequest request) {
        InscripcionResponse response = inscripcionService.inscribirEstudiante(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
