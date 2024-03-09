package com.makhmutov.internshipvkmarket.di

import com.makhmutov.internshipvkmarket.data.network.api.ProductsApiFactory
import com.makhmutov.internshipvkmarket.data.repository.MarketRepositoryImpl
import com.makhmutov.internshipvkmarket.domain.respository.MarketRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @ApplicationScope
    @Binds
    fun bindRepository(impl: MarketRepositoryImpl): MarketRepository

    companion object{
        @ApplicationScope
        @Provides
        fun provideApiService() = ProductsApiFactory.apiService
    }
}