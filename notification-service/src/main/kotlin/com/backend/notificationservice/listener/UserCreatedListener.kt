package com.backend.notificationservice.listener

import com.backend.notificationservice.config.RabbitMQConfig
import com.backend.notificationservice.dto.UserCreatedEvent
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

@Component
class UserCreatedListener {
    private val logger = LoggerFactory.getLogger(UserCreatedListener::class.java)
    
    @RabbitListener(queues = [RabbitMQConfig.QUEUE_NAME])
    fun handleEmailNotification(event: UserCreatedEvent) {
        logger.info("이메일 발송 완료: userId=${event.payload.userId}, name=${event.payload.name}, email=${event.payload.email}")
    }
}