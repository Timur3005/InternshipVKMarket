package com.example.auth_api.domain.entities

data class UserEntity(
    val name: String,
    val email: String,
    val password: String,
)