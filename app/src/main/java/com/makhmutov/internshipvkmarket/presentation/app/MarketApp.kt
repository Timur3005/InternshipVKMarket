package com.makhmutov.internshipvkmarket.presentation.app

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.makhmutov.internshipvkmarket.di.ApplicationComponent
import com.makhmutov.internshipvkmarket.di.DaggerApplicationComponent

class MarketApp : Application() {

    val component: ApplicationComponent by lazy {
        DaggerApplicationComponent.factory().create()
    }

}

@Composable
fun getApplicationComponent(): ApplicationComponent {
    return (LocalContext.current.applicationContext as MarketApp).component
}