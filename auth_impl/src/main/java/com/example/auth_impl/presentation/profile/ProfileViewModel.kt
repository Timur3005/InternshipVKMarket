package com.example.auth_impl.presentation.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.auth_api.domain.entities.TokenManager
import com.example.auth_api.domain.entities.UserEntity
import com.example.auth_impl.domain.repositories.AuthRepository
import com.example.auth_impl.presentation.nav.Screens
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val tokenManager: TokenManager
) : ViewModel() {

    private val _state = MutableStateFlow(
        ProfileState.AuthState(
            UserEntity(
                email = "",
                password = "",
                name = ""
            )
        )
    )

    val state = _state.asStateFlow()

    init {
        val name = tokenManager.parseToken().first
        val email = tokenManager.parseToken().second

        Log.d("TAG", name)

        _state.update {
            it.copy(
                UserEntity(
                    email = email,
                    password = "",
                    name = name
                )
            )
        }
    }

    private val _navigationFlow = MutableSharedFlow<String>(
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
        extraBufferCapacity = 1
    )

    val navigation = _navigationFlow.asSharedFlow()

    fun logout() {
        viewModelScope.launch {
            authRepository.logout().onSuccess {
                _navigationFlow.tryEmit(Screens.Authorization.name.lowercase())
            }
        }
    }
}