package com.example.auth_impl.di

import com.example.auth_impl.data.repositories.AuthRepositoryImpl
import com.example.auth_impl.data.repositories.network.api.AuthApiFactory
import com.example.auth_impl.data.repositories.network.api.AuthorizationApiService
import com.example.auth_impl.data.repositories.prefs.TokenManager
import com.example.auth_impl.data.repositories.prefs.TokenManagerImpl
import com.example.auth_impl.domain.repositories.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
internal abstract class AuthModule {

    @Binds
    abstract fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @Binds
    abstract fun bindAuthRepository(impl: TokenManagerImpl): TokenManager

    @Provides
    fun provideAuthApiService(): AuthorizationApiService = AuthApiFactory.apiService
}