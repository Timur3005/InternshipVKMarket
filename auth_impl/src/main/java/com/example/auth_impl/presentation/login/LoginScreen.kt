package com.example.auth_impl.presentation.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.auth_impl.R
import com.example.auth_impl.presentation.compose.Authorization

@Composable
internal fun LoginScreen(onNavigate: (String) -> Unit) {

    val viewModel: LoginViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.navigation.collect(onNavigate)
    }

    Authorization(
        userEntity = state.userEntity,
        navbarTitle = stringResource(R.string.logining),
        buttonTitle = stringResource(R.string.login),
        onEmailChange = viewModel::updateEmail,
        onPasswordChange = viewModel::updatePassword,
        onButtonPressed = viewModel::login
    )
}