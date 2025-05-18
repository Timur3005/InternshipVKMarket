package com.example.auth_impl.presentation.greeting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.auth_api.domain.entities.TokenManager
import com.example.auth_impl.presentation.nav.Screens
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class GreetingViewModel @Inject constructor(
    private val tokenManager: TokenManager,
) : ViewModel() {

    private val _neededScreen: MutableStateFlow<Screens?> = MutableStateFlow(null)
    val neededScreen: StateFlow<Screens?> = _neededScreen.asStateFlow()

    init {
        viewModelScope.launch {
            delay(1000L)
            if (tokenManager.getToken().isNullOrBlank()) {
                _neededScreen.emit(Screens.Authorization)
            } else {
                _neededScreen.emit(Screens.Products)
            }
        }
    }

}