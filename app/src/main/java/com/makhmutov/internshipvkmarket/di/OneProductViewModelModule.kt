package com.makhmutov.internshipvkmarket.di

import androidx.lifecycle.ViewModel
import com.makhmutov.internshipvkmarket.presentation.screens.oneproduct.OneProductViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface OneProductViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(OneProductViewModel::class)
    fun bindOneProductViewModelModule(impl: OneProductViewModel): ViewModel
}