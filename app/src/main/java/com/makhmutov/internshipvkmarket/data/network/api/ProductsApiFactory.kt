package com.makhmutov.internshipvkmarket.data.network.api

import com.example.auth_api.domain.entities.TokenManager
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class ProductsApiFactory(
    tokenManager: TokenManager
) {

    private val authInterceptor = Interceptor { chain ->
        val token = tokenManager.getToken()
        val request = chain.request().newBuilder().apply {
            token?.let {
                addHeader("Authorization", "Bearer $it")
            }
        }.build()
        chain.proceed(request)
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://ab4a-45-114-62-227.ngrok-free.app/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: ProductsApiService = retrofit.create()

}