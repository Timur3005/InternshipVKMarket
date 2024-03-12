package com.makhmutov.internshipvkmarket.data.network.api

import com.makhmutov.internshipvkmarket.data.network.models.RequestMarketItemsContainer
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductsApiService {

    @GET(MARKET_ITEMS_REQUEST)
    suspend fun getMarketItems(
        @Query("limit") limit: Int = LIMIT_MARKET_ITEMS_REQUEST,
        @Query("skip") skip: Int = DEFAULT_SKIP_VALUE,
    ): RequestMarketItemsContainer

    @GET(CATEGORIES_REQUEST)
    suspend fun getCategories(): List<String>

    @GET("$MARKET_ITEMS_BY_CATEGORY_REQUEST{category}")
    suspend fun getMarketItemsByCategory(
        @Path("category") category: String,
    ): RequestMarketItemsContainer

    private companion object {
        const val MARKET_ITEMS_REQUEST = "/products"
        const val CATEGORIES_REQUEST = "/products/categories"
        const val MARKET_ITEMS_BY_CATEGORY_REQUEST = "/products/category/"
        const val LIMIT_MARKET_ITEMS_REQUEST = 20
        const val DEFAULT_SKIP_VALUE = 0
    }

}