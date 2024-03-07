package com.makhmutov.internshipvkmarket.data.network.api

import com.makhmutov.internshipvkmarket.data.network.models.RequestMarketItemsContainer
import retrofit2.http.GET

interface ProductsApiService {

    @GET(MARKET_ITEMS_REQUEST)
    fun getMarketItems(): RequestMarketItemsContainer

    companion object {
        private const val MARKET_ITEMS_REQUEST = "/products"
    }
}