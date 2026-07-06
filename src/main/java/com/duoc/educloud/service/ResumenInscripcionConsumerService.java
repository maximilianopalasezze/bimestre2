package com.duoc.educloud.service;

import com.duoc.educloud.config.RabbitMQConfig;
import com.duoc.educloud.dto.ResumenInscripcionMensaje;
import com.duoc.educloud.model.ResumenInscripcionCola;
import com.duoc.educloud.repository.ResumenInscripcionColaRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ResumenInscripcionConsumerService {

    private final RabbitTemplate rabbitTemplate;
    private final ResumenInscripcionColaRepository resumenInscripcionColaRepository;

    public ResumenInscripcionConsumerService(
            RabbitTemplate rabbitTemplate,
            ResumenInscripcionColaRepository resumenInscripcionColaRepository
    ) {
        this.rabbitTemplate = rabbitTemplate;
        this.resumenInscripcionColaRepository = resumenInscripcionColaRepository;
    }

    @Transactional
    public ResumenInscripcionCola consumirYGuardarResumen() {

        Object mensajeRecibido = rabbitTemplate.receiveAndConvert(
                RabbitMQConfig.RESUMEN_INSCRIPCION_QUEUE
        );

        if (mensajeRecibido == null) {
            return null;
        }

        if (!(mensajeRecibido instanceof ResumenInscripcionMensaje mensaje)) {
            throw new IllegalStateException(
                    "El mensaje recibido no tiene el formato esperado de resumen de inscripción"
            );
        }

        ResumenInscripcionCola resumenGuardado = new ResumenInscripcionCola(
                mensaje.getIdInscripcion(),
                mensaje.getNombreEstudiante(),
                mensaje.getCorreoEstudiante(),
                mensaje.getTotalPagar(),
                mensaje.getFechaInscripcion(),
                mensaje.getDetalleResumen()
        );

        return resumenInscripcionColaRepository.save(resumenGuardado);
    }
}