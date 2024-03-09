package com.makhmutov.internshipvkmarket.di

import com.makhmutov.internshipvkmarket.presentation.activity.MainActivity
import com.makhmutov.internshipvkmarket.presentation.viewmodelfactory.ViewModelFactory
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        DataModule::class,
        ViewModelModule::class
    ]
)
interface ApplicationComponent {
    fun inject(activity: MainActivity)

    fun getViewModelFactory(): ViewModelFactory

    @Component.Factory
    interface Factory {
        fun create(): ApplicationComponent
    }
}