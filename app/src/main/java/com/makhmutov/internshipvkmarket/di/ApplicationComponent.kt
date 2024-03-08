package com.makhmutov.internshipvkmarket.di

import com.makhmutov.internshipvkmarket.presentation.MainActivity
import dagger.Component

@Component(
    modules = [
        DataModule::class,
        ViewModelModule::class
    ]
)
interface ApplicationComponent {
    fun inject(activity: MainActivity)

    @Component.Factory
    interface Factory{
        fun create(): ApplicationComponent
    }
}