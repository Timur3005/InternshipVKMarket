package com.example.auth_api.domain.entities

interface TokenManager {

    fun saveToken(token: String)

    fun getToken(): String?

    fun clearToken()

    fun parseToken(): Pair<String, String>
}