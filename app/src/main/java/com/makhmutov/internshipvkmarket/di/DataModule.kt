package com.makhmutov.internshipvkmarket.di

import android.content.Context
import androidx.room.Room
import com.example.auth_api.domain.entities.TokenManager
import com.makhmutov.internshipvkmarket.data.local.AppDatabase
import com.makhmutov.internshipvkmarket.data.local.MarketItemDao
import com.makhmutov.internshipvkmarket.data.network.api.ProductsApiFactory
import com.makhmutov.internshipvkmarket.data.repository.MarketRepositoryImpl
import com.makhmutov.internshipvkmarket.domain.respository.MarketRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

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

        @Provides
        @Singleton
        fun provideDao(@ApplicationContext context: Context): MarketItemDao {
            val db = Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "market.db"
            ).build()

            val dao = db.marketItemDao()

            return dao
        }
    }
}