package com.example.auth_impl.data.repositories

import android.content.Context
import com.example.auth_api.domain.entities.UserEntity
import com.example.auth_impl.data.repositories.mappers.toUserDto
import com.example.auth_impl.data.repositories.network.api.AuthorizationApiService
import com.example.auth_impl.data.repositories.prefs.TokenManager
import com.example.auth_impl.domain.repositories.AuthRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class AuthRepositoryImpl @Inject constructor(
    private val authorizationApiService: AuthorizationApiService,
    private val tokenManager: TokenManager,
    private val context: Context,
) : AuthRepository {

    override suspend fun login(userData: UserEntity): Result<Unit> = runCatching {
        val token = authorizationApiService.login(userData.toUserDto())
        tokenManager.saveToken(context, token.token)
    }

    override suspend fun logout(userData: UserEntity): Result<Unit> = runCatching {
        // TODO: логика выхода
        tokenManager.clearToken(context)
    }

    override suspend fun register(userData: UserEntity): Result<Unit> = runCatching {
        val id = authorizationApiService.register(userData.toUserDto()).id
        // TODO: логика регистрации
    }
}