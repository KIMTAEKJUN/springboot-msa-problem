package com.backend.notificationservice.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class UserCreatedEvent(
    @JsonProperty("metadata")
    val metadata: EventMetadata,
    @JsonProperty("payload")
    val payload: UserCreatedPayload
)

data class EventMetadata(
    @JsonProperty("eventType")
    val eventType: String = "user.created",
    @JsonProperty("eventVersion")
    val eventVersion: String = "v1",
    @JsonProperty("timestamp")
    val timestamp: String,
    @JsonProperty("source")
    val source: String = "user-service"
)

data class UserCreatedPayload(
    @JsonProperty("userId")
    val userId: String,
    @JsonProperty("name")
    val name: String,
    @JsonProperty("email")
    val email: String
)
