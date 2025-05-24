package com.example.auth_impl.data.repositories

import com.example.auth_api.domain.entities.TokenManager
import com.example.auth_api.domain.entities.UserEntity
import com.example.auth_impl.data.repositories.mappers.toUserDto
import com.example.auth_impl.data.repositories.network.api.AuthorizationApiService
import com.example.auth_impl.domain.repositories.AuthRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class AuthRepositoryImpl @Inject constructor(
    private val authorizationApiService: AuthorizationApiService,
    private val tokenManager: TokenManager,
) : AuthRepository {

    override suspend fun login(userData: UserEntity): Result<Unit> = runCatching {
        val token = authorizationApiService.login(userData.toUserDto())
        tokenManager.saveToken(token.token)
    }

    override suspend fun logout(): Result<Unit> = runCatching {
        tokenManager.clearToken()
    }

    override suspend fun register(userData: UserEntity): Result<Unit> = runCatching {
        val token = authorizationApiService.register(userData.toUserDto())
        tokenManager.saveToken(token.token)
    }
}