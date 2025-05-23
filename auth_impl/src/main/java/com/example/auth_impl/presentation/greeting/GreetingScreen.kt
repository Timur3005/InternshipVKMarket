package com.example.auth_impl.presentation.greeting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
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
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = stringResource(R.string.hi),
            style = TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
                lineHeight = 48.sp,
                letterSpacing = 0.5.sp
            )
        )
    }
}