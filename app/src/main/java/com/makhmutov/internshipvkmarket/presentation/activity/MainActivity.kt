package com.makhmutov.internshipvkmarket.presentation.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.makhmutov.internshipvkmarket.presentation.screens.main.MainScreen
import com.makhmutov.internshipvkmarket.presentation.screens.products.ProductsScreen
import com.makhmutov.internshipvkmarket.presentation.ui.theme.InternshipVKMarketTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InternshipVKMarketTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}