package com.backend.userservice.service

import com.backend.userservice.config.RabbitMQConfig
import com.backend.userservice.dto.EventMetadata
import com.backend.userservice.dto.UserCreatedEvent
import com.backend.userservice.dto.UserCreatedPayload
import com.backend.userservice.domain.User
import com.backend.userservice.repository.UserRepository
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID

@Service
class UserService(private val userRepository: UserRepository, private val rabbitTemplate: RabbitTemplate) {

    @Transactional
    fun createUser(name: String, email: String): User {
        if (userRepository.existsByEmail(email)) {
            throw IllegalArgumentException("이미 존재하는 이메일입니다.")
        }

        if (userRepository.existsByName(name)) {
            throw IllegalArgumentException("이미 존재하는 이름입니다.")
        }

        val userId = UUID.randomUUID().toString()
        val user = User(id = userId, name = name, email = email, createdAt = LocalDateTime.now())
        val savedUser = userRepository.save(user)

        val event = UserCreatedEvent(
            metadata = EventMetadata(
                eventType = "user.created",
                eventVersion = "v1",
                timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME),
                source = "user-service"
            ),
            payload = UserCreatedPayload(
                userId = savedUser.id!!,
                name = savedUser.name,
                email = savedUser.email
            )
        )

        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY, event)

        return savedUser
    }

    fun getUserById(userId: String): User? {
        return userRepository.findById(userId).orElse(null)
    }

    fun findAll(): List<User> {
        return userRepository.findAll()
    }
}