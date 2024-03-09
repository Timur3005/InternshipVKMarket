package com.makhmutov.internshipvkmarket.di

import com.makhmutov.internshipvkmarket.presentation.viewmodelfactory.ViewModelFactory
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(
    modules = [
        OneProductViewModelModule::class
    ]
)
interface OneProductComponent {
    fun getViewModelFactory(): ViewModelFactory

    @Subcomponent.Factory
    interface Factory {
        fun create(
            @BindsInstance productId: Int,
        ): OneProductComponent
    }
}