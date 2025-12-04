package com.backend.userservice.repository

import com.backend.userservice.domain.User
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : MongoRepository<User, String> {
    fun existsByEmail(email: String): Boolean
    fun existsByName(name: String): Boolean
}