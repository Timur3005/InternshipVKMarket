package com.example.auth_api.domain.entities

data class UserEntity(
    val email: String,
    val password: String,
    val name: String? = null,
)