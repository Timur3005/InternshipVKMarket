package com.example.auth_impl.presentation.registration

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.auth_impl.R
import com.example.auth_impl.presentation.compose.Authorization

@Composable
fun RegistrationScreen(onNavigate: (String) -> Unit) {

    val viewModel: RegistrationViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.navigation.collect(onNavigate)
    }

    Authorization(
        userEntity = state.userEntity,
        navbarTitle = stringResource(R.string.registration_navbar_title),
        buttonTitle = stringResource(R.string.registration_button_text),
        onEmailChange = viewModel::updateEmail,
        onPasswordChange = viewModel::updatePassword,
        onButtonPressed = viewModel::login
    )
}