package com.example.auth_impl.presentation.greeting

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.auth_impl.R
import kotlinx.coroutines.flow.filterNotNull

@Composable
fun GreetingScreen(
    onNavigate: (String) -> Unit
) {
    val viewModel: GreetingViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        viewModel.neededScreen.filterNotNull().collect {
            onNavigate(it.name.lowercase())
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(stringResource(R.string.hi))
    }
}