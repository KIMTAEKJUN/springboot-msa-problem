package com.backend.userservice.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "users")
data class User(
    @Id
    val id: String? = null,
    val name: String,
    val email: String,
    val createdAt: LocalDateTime = LocalDateTime.now()
)