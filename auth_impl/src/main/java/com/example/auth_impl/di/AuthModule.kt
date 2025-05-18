package com.example.auth_impl.di

import com.example.auth_api.domain.entities.TokenManager
import com.example.auth_impl.data.repositories.AuthRepositoryImpl
import com.example.auth_impl.data.repositories.network.api.AuthApiFactory
import com.example.auth_impl.data.repositories.network.api.AuthorizationApiService
import com.example.auth_impl.data.repositories.prefs.TokenManagerImpl
import com.example.auth_impl.domain.repositories.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface AuthModule {

    @Binds
    fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @Binds
    fun bindTokenManager(impl: TokenManagerImpl): TokenManager

    companion object {
        @Provides
        fun provideAuthApiService(): AuthorizationApiService = AuthApiFactory.apiService
    }
}