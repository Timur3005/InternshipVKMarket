package com.example.auth_impl.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.auth_api.domain.entities.UserEntity
import com.example.auth_impl.domain.repositories.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(
        AuthScreenState.AuthState(
            userEntity = UserEntity("", "")
        )
    )

    val state = _state.asStateFlow()

    private val _navigationFlow = MutableSharedFlow<String>(
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
        extraBufferCapacity = 1
    )

    val navigation = _navigationFlow.asSharedFlow()

    fun login() {
        viewModelScope.launch {
            _state.update {
                it.copy(responseState = ResponseState.Loading)
            }
            authRepository.login(state.value.userEntity)
                .onSuccess {
                    _state.update {
                        it.copy(responseState = ResponseState.Success)
                    }
                    delay(100L)
                    // TODO: Поправить навигацию Alcuberrie
                    _navigationFlow.tryEmit("products")
                }
                .onFailure {
                    _state.update {
                        it.copy(responseState = ResponseState.Error)
                    }
                }
        }
    }

    fun updateEmail(email: String) {
        val state = _state.value
        _state.value = state.copy(
            userEntity = state.userEntity.copy(
                email = email
            )
        )
    }

    fun updatePassword(password: String) {
        val state = _state.value
        _state.value = state.copy(
            userEntity = state.userEntity.copy(
                password = password
            )
        )
    }
}