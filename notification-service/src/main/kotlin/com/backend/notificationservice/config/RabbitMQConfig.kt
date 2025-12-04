package com.backend.notificationservice.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.amqp.core.*
import org.springframework.amqp.rabbit.annotation.EnableRabbit
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableRabbit
class RabbitMQConfig {
    companion object {
        const val EXCHANGE_NAME = "user-exchange"
        const val QUEUE_NAME = "user-created-queue"
        const val ROUTING_KEY = "user.created.v1"
    }

    @Bean
    fun userExchange(): DirectExchange {
        return DirectExchange(EXCHANGE_NAME, true, false)
    }

    @Bean
    fun userCreatedQueue(): Queue {
        return Queue(QUEUE_NAME, true, false, false)
    }

    @Bean
    fun binding(): Binding {
        return BindingBuilder
            .bind(userCreatedQueue())
            .to(userExchange())
            .with(ROUTING_KEY)
    }

    @Bean
    fun messageConverter(): Jackson2JsonMessageConverter {
        val objectMapper = ObjectMapper().registerModule(KotlinModule.Builder().build())
        return Jackson2JsonMessageConverter(objectMapper)
    }
}