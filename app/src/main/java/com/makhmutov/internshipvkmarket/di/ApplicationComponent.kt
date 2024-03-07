package com.makhmutov.internshipvkmarket.di

import com.makhmutov.internshipvkmarket.MainActivity
import dagger.Component

@Component(
    modules = [
        DataModule::class
    ]
)
interface ApplicationComponent {
    fun inject(activity: MainActivity)

    @Component.Factory
    interface Factory{
        fun create(): ApplicationComponent
    }
}