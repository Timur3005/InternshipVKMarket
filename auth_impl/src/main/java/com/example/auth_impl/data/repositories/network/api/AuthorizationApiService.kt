package com.example.auth_impl.data.repositories.network.api

import com.example.auth_impl.data.repositories.network.models.AuthTokenDto
import com.example.auth_impl.data.repositories.network.models.UserDto
import retrofit2.http.Body
import retrofit2.http.POST

internal interface AuthorizationApiService {

    @POST("/auth/login")
    suspend fun login(@Body userData: UserDto): AuthTokenDto

    @POST("auth/register")
    suspend fun register(@Body userData: UserDto): AuthTokenDto
}