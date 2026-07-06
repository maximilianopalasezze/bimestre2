package com.duoc.educloud.service;

import com.duoc.educloud.config.RabbitMQConfig;
import com.duoc.educloud.dto.ResumenInscripcionMensaje;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class ResumenInscripcionProducerService {

    private final RabbitTemplate rabbitTemplate;

    public ResumenInscripcionProducerService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void enviarResumen(ResumenInscripcionMensaje mensaje) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.RESUMEN_INSCRIPCION_EXCHANGE,
                RabbitMQConfig.RESUMEN_INSCRIPCION_ROUTING_KEY,
                mensaje,
                message -> {
                    message.getMessageProperties()
                            .setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                    return message;
                }
        );
    }
}