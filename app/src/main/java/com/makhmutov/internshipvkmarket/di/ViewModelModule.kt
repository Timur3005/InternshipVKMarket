package com.makhmutov.internshipvkmarket.di

import androidx.lifecycle.ViewModel
import com.makhmutov.internshipvkmarket.presentation.screens.products.ProductsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {
    @IntoMap
    @Binds
    @ViewModelKey(ProductsViewModel::class)
    fun bindProductsViewModel(impl: ProductsViewModel): ViewModel

}