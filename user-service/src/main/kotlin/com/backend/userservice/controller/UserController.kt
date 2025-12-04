package com.backend.userservice.controller

import com.backend.userservice.domain.User
import com.backend.userservice.service.UserService
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller

@Controller
class UserController(private val userService: UserService) {
    @QueryMapping
    fun user(@Argument id: String): User? {
        return userService.getUserById(id)
    }
    
    @QueryMapping
    fun users(): List<User> {
        return userService.findAll()
    }
    
    @MutationMapping
    fun createUser(@Argument name: String, @Argument email: String): User {
        return userService.createUser(name, email)
    }
}