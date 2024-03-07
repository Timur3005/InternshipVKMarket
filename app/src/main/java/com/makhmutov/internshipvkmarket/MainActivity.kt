package com.makhmutov.internshipvkmarket

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.makhmutov.internshipvkmarket.domain.respository.MarketRepository
import com.makhmutov.internshipvkmarket.ui.theme.InternshipVKMarketTheme
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : ComponentActivity() {
    @Inject
    lateinit var repository: MarketRepository

    private val component by lazy {
        (application as MarketApp).component
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        lifecycleScope.launch {
            repository.requestMarketItems()
            repository.marketItemsFlow.collect{
                Log.d("MainActivity", it.toString())
            }
        }
        super.onCreate(savedInstanceState)
        setContent {
            InternshipVKMarketTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                }
            }
        }
    }
}