package com.example.auth_impl.di

import android.content.Context
import dagger.Component

@Component(
    modules = [
        AuthModule::class
    ]
)
internal interface AuthComponent {

    @Component.Factory
    interface Factory {
        fun create(
            context: Context
        ): AuthComponent
    }
}