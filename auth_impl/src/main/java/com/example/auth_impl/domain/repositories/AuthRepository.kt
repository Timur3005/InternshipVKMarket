package com.example.auth_impl.domain.repositories

import com.example.auth_api.domain.entities.UserEntity

internal interface AuthRepository {
    suspend fun login(userData: UserEntity): Result<Unit>
    suspend fun logout(): Result<Unit>
    suspend fun register(userData: UserEntity): Result<Unit>
}