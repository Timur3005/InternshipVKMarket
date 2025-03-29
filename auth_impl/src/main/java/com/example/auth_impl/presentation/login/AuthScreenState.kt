package com.example.auth_impl.presentation.login

import androidx.compose.runtime.Stable
import com.example.auth_api.domain.entities.UserEntity

@Stable
internal sealed interface AuthScreenState {

    data class AuthState(
        val userEntity: UserEntity,
        val responseState: ResponseState? = null
    ) : AuthScreenState
}

internal enum class ResponseState {
    Loading,
    Success,
    Error,
}