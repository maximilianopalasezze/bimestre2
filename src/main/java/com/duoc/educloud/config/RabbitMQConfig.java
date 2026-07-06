package com.duoc.educloud.config;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String RESUMEN_INSCRIPCION_QUEUE =
            "educloud.resumen.inscripcion.queue";

    public static final String RESUMEN_INSCRIPCION_EXCHANGE =
            "educloud.resumen.inscripcion.exchange";

    public static final String RESUMEN_INSCRIPCION_ROUTING_KEY =
            "educloud.resumen.inscripcion";

    @Bean
    public AmqpAdmin amqpAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public Queue resumenInscripcionQueue() {
        return QueueBuilder
                .durable(RESUMEN_INSCRIPCION_QUEUE)
                .build();
    }

    @Bean
    public DirectExchange resumenInscripcionExchange() {
        return ExchangeBuilder
                .directExchange(RESUMEN_INSCRIPCION_EXCHANGE)
                .durable(true)
                .build();
    }

    @Bean
    public Binding resumenInscripcionBinding(
            Queue resumenInscripcionQueue,
            DirectExchange resumenInscripcionExchange
    ) {
        return BindingBuilder
                .bind(resumenInscripcionQueue)
                .to(resumenInscripcionExchange)
                .with(RESUMEN_INSCRIPCION_ROUTING_KEY);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}