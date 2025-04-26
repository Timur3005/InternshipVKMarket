package com.example.auth_impl.presentation.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.auth_api.domain.entities.UserEntity
import com.example.auth_impl.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun Authorization(
    userEntity: UserEntity,
    navbarTitle: String,
    buttonTitle: String,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onButtonPressed: () -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(navbarTitle)
                }
            )
        },
        bottomBar = {
            Button(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                onClick = onButtonPressed,
            ) {
                Text(buttonTitle)
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues).fillMaxSize(),
            verticalArrangement = Arrangement.Center,
        ) {
            TextField(
                placeholder = {
                    Text(text = stringResource(R.string.auth_email))
                },
                value = userEntity.email,
                onValueChange = onEmailChange
            )
            TextField(
                modifier = Modifier.padding(top = 16.dp),
                placeholder = {
                    Text(text = stringResource(R.string.password))
                },
                value = userEntity.password,
                onValueChange = onPasswordChange
            )
        }
    }
}