package com.example.auth_impl.presentation.profile

import androidx.compose.runtime.Stable
import com.example.auth_api.domain.entities.UserEntity

@Stable
internal sealed interface ProfileState {

    data class AuthState(
        val userEntity: UserEntity,
    ) : ProfileState
}