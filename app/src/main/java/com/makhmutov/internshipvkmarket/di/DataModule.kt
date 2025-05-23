package com.makhmutov.internshipvkmarket.di

import com.example.auth_api.domain.entities.TokenManager
import com.makhmutov.internshipvkmarket.data.network.api.ProductsApiFactory
import com.makhmutov.internshipvkmarket.data.repository.MarketRepositoryImpl
import com.makhmutov.internshipvkmarket.domain.respository.MarketRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindRepository(impl: MarketRepositoryImpl): MarketRepository

    companion object {
        @Provides
        fun provideApiService(
            tokenManager: TokenManager
        ) = ProductsApiFactory(tokenManager).apiService
    }
}