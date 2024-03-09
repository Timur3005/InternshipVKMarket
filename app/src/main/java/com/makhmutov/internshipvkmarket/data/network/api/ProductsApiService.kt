package com.makhmutov.internshipvkmarket.data.network.api

import com.makhmutov.internshipvkmarket.data.network.models.RequestMarketItemsContainer
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductsApiService {

    @GET(MARKET_ITEMS_REQUEST)
    suspend fun getMarketItems(
        @Query("limit") limit: Int = LIMIT_MARKET_ITEMS_REQUEST,
        @Query("skip") skip: Int = DEFAULT_SKIP_VALUE,
    ): RequestMarketItemsContainer

    private companion object {
        const val MARKET_ITEMS_REQUEST = "/products"
        const val LIMIT_MARKET_ITEMS_REQUEST = 20
        const val DEFAULT_SKIP_VALUE = 0
    }

}